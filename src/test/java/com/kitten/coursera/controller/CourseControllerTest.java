package com.kitten.coursera.controller;

import com.kitten.coursera.dto.CourseDto;
import com.kitten.coursera.dto.mapper.CourseMapper;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    CourseService courseService;
    @Mock
    CourseMapper courseMapper;
    @InjectMocks
    CourseController controller;

    @Test
    void findAllUsers_ReturnsUsersList() {
        Course course1 = Course.builder()
            .id(UUID.randomUUID())
            .author("Testing test")
            .title("Testing test")
            .description("Testing test")
            .build();
        Course course2 = Course.builder()
            .id(UUID.randomUUID())
            .author("Testing test")
            .title("Testing test")
            .description("Testing test")
            .build();
        List<Course>courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        System.out.println(courses);

        Mockito.doReturn(courses).when(this.courseService).readAllCourses();
        CourseDto courseDto = CourseDto.builder()
            .title(course1.getTitle())
            .author(course1.getAuthor())
            .description(course1.getDescription())
            .build();
        CourseDto courseDto2 = CourseDto.builder()
            .title(course2.getTitle())
            .author(course2.getAuthor())
            .description(course2.getDescription())
            .build();

        var coursesDto = new ArrayList<>();
        coursesDto.add(courseDto);
        coursesDto.add(courseDto2);
        System.out.println(coursesDto);

        Mockito.doReturn(coursesDto).when(this.courseMapper).mapCoursesToDto(courses);

       var re = this.controller.readAll();

       assertNotNull(re);
       assertEquals(HttpStatus.OK, re.getStatusCode());
       assertEquals(MediaType.APPLICATION_JSON, re.getHeaders().getContentType());
       assertEquals(coursesDto, re.getBody());

    }

}