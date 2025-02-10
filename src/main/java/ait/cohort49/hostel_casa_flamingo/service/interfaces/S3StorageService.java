package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public interface S3StorageService {

    void uploadFile(String s3Path, MultipartFile file);

    URL generatePresignedUrl(String s3Path);

    void deleteFile(String s3Path);
}
