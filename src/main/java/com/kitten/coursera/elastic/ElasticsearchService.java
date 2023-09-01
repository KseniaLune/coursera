package com.kitten.coursera.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kitten.coursera.domain.entity.CourseElastic;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElasticsearchService {
    private final ElasticsearchClient client;

    public SearchResponse<Map> matchAll() throws IOException {
        Supplier<Query> supplier = ElasticUtils.supplier();
        SearchResponse<Map> searchResponse = client.search(s -> s.query(supplier.get()), Map.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<CourseElastic> matchAllCourses() throws IOException {
        Supplier<Query> supplier = ElasticUtils.supplier();
        SearchResponse<CourseElastic> searchResponse = client.search(s -> s.index("courses").query(supplier.get()), CourseElastic.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public Set<CourseElastic> matchCoursesWithTitleAndDescription(String fieldValue) throws IOException {
        Supplier<Query> supplierTitle = ElasticUtils.supplierWithTitleField(fieldValue);
        Supplier<Query> supplierDescription = ElasticUtils.supplierWithDescriptionField(fieldValue);

        SearchResponse<CourseElastic> searchResponseTitle = client.search(s -> s.index("courses").query(supplierTitle.get()), CourseElastic.class);
        SearchResponse<CourseElastic> searchResponseDescription = client.search(s -> s.index("courses").query(supplierDescription.get()), CourseElastic.class);

        Set<CourseElastic> result = searchResponseTitle.hits().hits().stream()
            .map(Hit::source).collect(Collectors.toSet());
        for (Hit<CourseElastic> hits: searchResponseDescription.hits().hits()) {
            result.add(hits.source());
        }

        return result;
    }

    public SearchResponse<CourseElastic> fuzzyProductWithTitle(String approximateCourseTitle) throws IOException {
        Supplier<Query> supplier = ElasticUtils.supplierFuzzyQueryWithTitle(approximateCourseTitle);
        SearchResponse<CourseElastic> searchResponse = client.search(s -> s.index("courses").query(supplier.get()), CourseElastic.class);
        return searchResponse;
    }

    public SearchResponse<CourseElastic> wildcardProductWithTitle(String partCourseTitle) throws IOException {
        Supplier<Query> supplier = ElasticUtils.supplierWildcardQueryWithTitle(partCourseTitle);
        SearchResponse<CourseElastic> searchResponse = client.search(s -> s.index("courses").query(supplier.get()), CourseElastic.class);
        return searchResponse;
    }


}