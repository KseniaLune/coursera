package com.kitten.coursera.service.impl;

import com.kitten.coursera.domain.entity.UserAvatar;
import com.kitten.coursera.domain.exception.AvatarUploadEx;
import com.kitten.coursera.service.UserAvatarService;
import com.kitten.coursera.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String addNewAvatar(UserAvatar ava) {
        log.info("avatar: "+ava);
        try {
            createBucket();
        } catch (Exception e) {
            throw new AvatarUploadEx("Avatar upload failed" + e.getMessage());
        }

        log.info("avatar, getFile" +ava.getFile());
        MultipartFile file = ava.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new AvatarUploadEx("Avatar must have name");
        }

        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new AvatarUploadEx("Avatar upload failed" + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(minioProperties.getBucket())
            .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile file) {

        return file.getOriginalFilename()
            .substring(file.getOriginalFilename().lastIndexOf("." )+1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
            .stream(inputStream, inputStream.available(), -1)
            .bucket(minioProperties.getBucket())
            .object(fileName)
            .build());
    }

}
