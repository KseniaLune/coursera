package com.kitten.coursera.elastic;

import co.elastic.clients.elasticsearch._types.query_dsl.*;

import java.util.function.Supplier;

public class ElasticUtils {

    public static Supplier<Query> supplier() {
        Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery() {
        return new MatchAllQuery.Builder().build();
    }

    public static Supplier<Query> supplierWithTitleField(String fieldValue) {
        Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithTitleField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithTitleField(String fieldValue) {
        return new MatchQuery.Builder()
            .field("title")
            .query(fieldValue)
            .build();
    }
    public static Supplier<Query> supplierWithDescriptionField(String fieldValue) {
        Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithDescriptionField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithDescriptionField(String fieldValue) {
        return new MatchQuery.Builder()
            .field("description")
            .query(fieldValue)
            .build();
    }

    public static Supplier<Query> supplierFuzzyQueryWithTitle(String approximateCourseTitle) {
        Supplier<Query> supplier = () -> Query.of(q -> q.fuzzy(createFuzzyQuery(approximateCourseTitle)));
        return supplier;
    }

    public static FuzzyQuery createFuzzyQuery(String approximateCourseTitle) {
        return new FuzzyQuery.Builder()
            .field("title")
            .value(approximateCourseTitle)
            .build();
    }
    public static Supplier<Query> supplierWildcardQueryWithTitle(String partCourseTitle) {
        Supplier<Query> supplier = () -> Query.of(q -> q.wildcard(createWildcardQuery(partCourseTitle)));
        return supplier;
    }

    public static WildcardQuery createWildcardQuery(String partCourseTitle) {
        String wildcard = partCourseTitle+"*";
        return new WildcardQuery.Builder()
            .field("title")
            .wildcard(wildcard)
            .build();
    }
}

