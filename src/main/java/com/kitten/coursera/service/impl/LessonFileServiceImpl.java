package com.kitten.coursera.service.impl;

import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.domain.exception.FileUploadEx;
import com.kitten.coursera.service.LessonFileService;
import com.kitten.coursera.service.props.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonFileServiceImpl implements LessonFileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String addFile(LessonFile lessonFile) {

        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadEx("File upload exception " + e.getMessage());
        }
        MultipartFile file = lessonFile.getFile();
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
        saveFile(inputStream, filename);
        return filename;

    }

    @SneakyThrows
    private void createBucket() {
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(minioProperties.getBucketLessonFile())
            .build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(minioProperties.getBucketLessonFile())
                .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = this.getExtension(file);
        return UUID.randomUUID() + "." + extension;

    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
            .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String filename) {
        minioClient.putObject(PutObjectArgs.builder()
            .stream(inputStream, inputStream.available(), -1)
            .bucket(minioProperties.getBucketLessonFile())
            .object(filename)
            .build());
    }

    @Override
    public void downloadFile(String filename) throws Exception {
        minioClient.downloadObject(DownloadObjectArgs.builder()
            .bucket(minioProperties.getBucketLessonFile())
            .object(filename)
            .filename("/Users/andrew/Downloads/new.pdf")
            .build());
    }


}
