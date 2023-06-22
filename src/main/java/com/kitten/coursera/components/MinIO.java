package com.kitten.coursera.components;

import org.springframework.web.multipart.MultipartFile;

public interface MinIO {
     String uploadFile(MultipartFile file, String bucket);
     void downloadFile(String objectName, String bucket);
     String showFile (String objectName, String bucket);
}
