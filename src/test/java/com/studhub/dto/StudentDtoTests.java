package com.studhub.dto;

import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import com.studhub.entity.Student;
import com.studhub.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@WebAppConfiguration
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class StudentDtoTests {
    @Autowired
    private UserService userService;

//    @Autowired
//    private studentService studentService;

//    @Before
//    @Test
//    public void contextLoads() {
//        Assert.assertNotNull(studentService);
//    }

    @Test
    @Transactional
    public void testStudentDtoConstructor() {
        Student student = (Student)userService.getById(2L);
        StudentDto studentDto = new StudentDto(student);
        Assert.assertEquals(student.getId(), studentDto.getId());
        Assert.assertEquals(student.getFirstName(), studentDto.getFirstName());
        Assert.assertEquals(student.getLastName(), studentDto.getLastName());
        Assert.assertEquals(student.getStatus(), studentDto.getStatus());
        Assert.assertEquals(student.getUsername(), studentDto.getUsername());

        List<Course> courses = student.getCourses();
        Assert.assertEquals(1, courses.size());

        Course course = courses.get(0);
        Assert.assertEquals(1L, course.getId().longValue());
        Assert.assertEquals(CourseStatus.ACTIVE, course.getCourseStatus());
    }
}
