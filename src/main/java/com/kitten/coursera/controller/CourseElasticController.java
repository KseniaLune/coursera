package com.kitten.coursera.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kitten.coursera.domain.entity.CourseElastic;
import com.kitten.coursera.elastic.ElasticsearchService;
import com.kitten.coursera.elastic.CourseElasticService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/elastic")
public class CourseElasticController {

    private final CourseElasticService service;
    private final ElasticsearchService elasticsearchService;


    @GetMapping("/match_all")
    public String matchAll() {
        try {
            SearchResponse<Map> mapSearchResponse = elasticsearchService.matchAll();
            return mapSearchResponse.hits().hits().toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/match_all_courses")
    public List<CourseElastic> matchAllCourses() {
        try {
            SearchResponse<CourseElastic> searchResponse = elasticsearchService.matchAllCourses();
            List<Hit<CourseElastic>> hits = searchResponse.hits().hits();

            return hits.stream()
                .map(Hit::source)
                .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/match_courses_with_title_and_description/{fieldValue}")
    public Set<CourseElastic> matchCourseWithTitleAndDescription(@PathVariable("fieldValue") String fieldValue) {
        try {
            return elasticsearchService.matchCoursesWithTitleAndDescription(fieldValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/fuzzy_courses_with_title/{approximateCoursesTitle}")
    public List<CourseElastic> fuzzyCoursesWithTitle(@PathVariable("approximateCoursesTitle") String approximateCoursesTitle) {
        try {
            SearchResponse<CourseElastic> searchResponse = elasticsearchService.fuzzyProductWithTitle(approximateCoursesTitle);
            List<Hit<CourseElastic>> hits = searchResponse.hits().hits();

            return hits.stream()
                .map(Hit::source)
                .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/wildcard_courses_with_title/{partCourseTitle}")
    public List<CourseElastic> wildcardCoursesWithTitle(@PathVariable("partCourseTitle") String partCourseTitle) {
        try {
            SearchResponse<CourseElastic> searchResponse = elasticsearchService.wildcardProductWithTitle(partCourseTitle);
            List<Hit<CourseElastic>> hits = searchResponse.hits().hits();

            return hits.stream()
                .map(Hit::source)
                .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
