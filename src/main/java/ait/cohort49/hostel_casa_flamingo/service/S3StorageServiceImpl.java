package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

@Service
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${s3.bucketName}")
    private String bucketName;

    public S3StorageServiceImpl(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    /**
     * Загрузка файла в S3
     */
    @Override
    public void uploadFile(String s3Path, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Path)
                    .build();
            RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

            s3Client.putObject(putObjectRequest, requestBody);

        } catch (IOException e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
        }
    }

    /**
     * Генерация `Presigned URL` для временного доступа к файлу (например, 1 час)
     */
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
    public void deleteFile(String s3Path) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Path)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

    }
}