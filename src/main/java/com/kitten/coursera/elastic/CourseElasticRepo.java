package com.kitten.coursera.elastic;

import com.kitten.coursera.domain.entity.CourseElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface CourseElasticRepo extends ElasticsearchRepository<CourseElastic, UUID> {
}
