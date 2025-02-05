package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
public class S3StorageServiceImpl implements S3StorageService {


    private final S3AsyncClient s3AsyncClient;
    private final S3Presigner s3Presigner;

    @Value("${s3.bucketName}")
    private String bucketName;

    public S3StorageServiceImpl(S3AsyncClient s3AsyncClient, S3Presigner s3Presigner) {
        this.s3AsyncClient = s3AsyncClient;
        this.s3Presigner = s3Presigner;
    }


    /**
     * Загрузка файла в S3
     */
    @Override
    public void uploadFile(String bucketName, String key, InputStream inputStream) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3AsyncClient.putObject(putObjectRequest, AsyncRequestBody.fromInputStream(inputStream, -1));
    }

    /**
     * Генерация `Presigned URL` для временного доступа к файлу (например, 1 час)
     */
    @Override
    public URL generatePresignedUrl(String s3Path) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(req -> req.bucket(bucketName).key(s3Path))
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url();
    }

    /**
     * Удаление файла из S3
     */
    @Override
    public CompletableFuture<Void> deleteFile(String s3Path) {
        return s3AsyncClient.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Path)
                        .build())
                .thenRun(() -> System.out.println("File deleted from S3: " + s3Path));
    }
}
