package com.kitten.coursera.repo;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface UserRepo extends JpaRepository<AppUser, UUID> {

    @Query(value = "SELECT c_course_id as c_id FROM t_user_course t WHERE t.c_usr_id=:id", nativeQuery = true)
    List<UUID> findUUIDCourseByUserId(@Param("id") UUID id);

    @Transactional
    @Query(value = "SELECT new Course(tc.id, tc.title, tc.description, tc.author) " +
        "FROM Course tc " +
        "JOIN UserToCourse tuc ON tc.id = tuc.courseId " +
        "JOIN AppUser tu ON tuc.userId = tu.id " +
        "WHERE tu.id = :id")
    List<Course> findCourseByUserId(@Param("id") UUID id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO t_user_course (c_usr_id, c_course_id) values (:user_id, :course_id)", nativeQuery = true)
    void signUp(@Param("user_id") UUID user_id, @Param("course_id") UUID course_id);

}
