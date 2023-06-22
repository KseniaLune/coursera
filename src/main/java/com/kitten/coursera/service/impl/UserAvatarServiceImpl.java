package com.kitten.coursera.service.impl;

import com.kitten.coursera.components.MinIO;
import com.kitten.coursera.domain.entity.UserAvatar;
import com.kitten.coursera.domain.exception.FileUploadEx;
import com.kitten.coursera.service.UserAvatarService;
import com.kitten.coursera.service.props.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Extension;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    private final MinioProperties minioProperties;
    private final MinIO minIO;

    @Override
    public String addNewAvatar(UserAvatar ava) {
        return minIO.uploadFile(ava.getFile(), minioProperties.getBucketAvatar());
    }

    @Override
    public String showAvatar(String objectName) {
        return minIO.showFile(objectName, minioProperties.getBucketAvatar());
    }

}
