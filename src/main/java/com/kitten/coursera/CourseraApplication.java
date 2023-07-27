package com.kitten.coursera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CourseraApplication {

    public static void main(String[] args) {
         SpringApplication.run(CourseraApplication.class, args);
    }

}
