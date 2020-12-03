package com.studhub.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ToStringTests {

    private User user;
    private Student student;
    private Course course;
    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("best_user");

        List<Course> courses = new ArrayList<>();
        student = new Student();
        student.setId(2L);
        student.setUsername("best_student");
        student.setCourses(courses);

        course = new Course();
        course.setId(1L);
        course.setStudent(student);
        courses.add(course);

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setCourse(course);
        lesson.setTopic("All calculus in 1 lesson.");
    }

    @Test
    public void testEntitiesToString() {
        Assert.assertEquals("User{id=1, username='best_user'}", user.toString());
        Assert.assertEquals("Student{id=2, username='best_student'}", student.toString());
        Assert.assertEquals("Course{id=1, student='best_student'}", course.toString());
        Assert.assertEquals("Lesson{id=1}", lesson.toString());
        Role role = new Role();
        role.setName("ADMIN");
        Assert.assertEquals("Role{ADMIN}", role.toString());
    }
}
