package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public interface S3StorageService {

    void uploadFile(String bucketName, String key, InputStream inputStream);

    URL generatePresignedUrl(String s3Path);

    CompletableFuture<Void> deleteFile(String s3Path);
}
