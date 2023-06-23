package com.kitten.coursera.service.impl;

import com.kitten.coursera.components.MinIO;
import com.kitten.coursera.domain.entity.LessonFile;
import com.kitten.coursera.service.LessonFileService;
import com.kitten.coursera.service.props.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonFileServiceImpl implements LessonFileService {

    private final MinioProperties minioProperties;
    private final MinIO minIO;

    @Override
    public String addFile(LessonFile lessonFile) {
        return minIO.uploadFile(lessonFile.getFile(), minioProperties.getBucketLessonFile());
    }

    @Override
    public void downloadFile(String objectName) {
        minIO.downloadFile(objectName, minioProperties.getBucketLessonFile());
    }

    @Override
    public String showFile(String objectName){
        return minIO.showFile(objectName,minioProperties.getBucketLessonFile());
    }

}
