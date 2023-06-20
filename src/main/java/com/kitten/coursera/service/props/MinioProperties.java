package com.kitten.coursera.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String bucketAvatar;
    private String bucketLessonFile;
    private String url;
    private String accessKey;
    private String secretKey;

}
