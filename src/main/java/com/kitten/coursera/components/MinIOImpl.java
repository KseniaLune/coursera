package com.kitten.coursera.components;

import com.kitten.coursera.domain.exception.FileDownloadEx;
import com.kitten.coursera.domain.exception.FileUploadEx;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MinIOImpl implements MinIO {
    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file, String basket) {
        try {
            createBucket(basket);
        } catch (Exception e) {
            throw new FileUploadEx("File upload exception " + e.getMessage());
        }
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadEx("File must have the name.");
        }
        String filename = generateFileName(file);
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadEx("File upload exception " + e.getMessage());
        }
        saveFile(inputStream, filename, basket);
        return filename;
    }

    @Override
    public void downloadFile(String objectName, String basket) {
        String extension = "." + this.getExtension(objectName);
        String originalName = this.getOriginalName(objectName);
        int count = 0;
        this.download(basket, objectName, originalName, extension, count);
    }

    private void download(String basket, String objectName, String originalName, String extension, int count) {
        try {
            if (count == 0) {
                minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(basket)
                    .object(objectName)
                    .filename("/Users/andrew/Downloads/" + originalName + extension)
                    .build());
            } else {
                minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(basket)
                    .object(objectName)
                    .filename("/Users/andrew/Downloads/" + originalName + "(" + count + ")" + extension)
                    .build());
            }
        } catch (Exception e) {
            count++;
            if (count <= 10) {
                download(basket, objectName, originalName, extension, count);
            } else {
                throw new FileDownloadEx("File download exception " + e.getMessage());
            }
        }
    }

    @Override
    public String showFile(String objectName, String basket) {
        String extension = this.getExtension(objectName);
        Map<String, String> reqParams = new HashMap<>();
        switch (extension){
            case "pdf" -> reqParams.put("response-content-type", "application/pdf");
            case "mp3" -> reqParams.put("response-content-type", "audio/mp3");
            case "gif" -> reqParams.put("response-content-type", "image/gif");
            case "png" -> reqParams.put("response-content-type", "image/png");
            case "mp4" -> reqParams.put("response-content-type", "video/mp4");
            case "mpeg" -> reqParams.put("response-content-type", "video/mpeg");
        }
        try {
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(basket)
                .object(objectName)
                .expiry(7, TimeUnit.DAYS)
                .extraQueryParams(reqParams)
                .build());
            return presignedObjectUrl;
        } catch (Exception e) {
            throw new FileUploadEx("qwerty"+ e.getMessage());
        }
    }

    @SneakyThrows
    private void createBucket(String bucket) {
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(bucket)
            .build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucket)
                .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String originalName = this.getShortName(file);
        String extension = this.getExtension(file);
        return originalName + "-" + UUID.randomUUID() + "." + extension;

    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
            .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    private String getExtension(String fileName) {
        return fileName
            .substring(fileName.lastIndexOf(".") + 1);
    }

    private String getOriginalName(String objectName) {
        String withoutExtension = objectName.substring(0, objectName.lastIndexOf(".") + 1);
        int indexUUID = (withoutExtension.length() - 1) - 36 - 1;
        String name = withoutExtension.substring(0, indexUUID);
        return name;
    }

    private String getShortName(MultipartFile file) {
        String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        if (name.length() >= 150) {
            return name.substring(0, 149);
        } else {
            return name;
        }
    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String filename, String bucket) {
        minioClient.putObject(PutObjectArgs.builder()
            .stream(inputStream, inputStream.available(), -1)
            .bucket(bucket)
            .object(filename)
            .build());
    }
}
