package com.studhub.service;

import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class CourseServiceTests {
    @Autowired
    private CourseService courseService;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(courseService);
    }

    @Test
    public void testGetById() {
        Course course = courseService.getById(1L).orElse(null);
        Assert.assertNotNull(course);
        Assert.assertEquals(1, course.getId().longValue());
        Assert.assertEquals("petr", course.getStudent().getUsername());
        Assert.assertEquals(CourseStatus.ACTIVE, course.getCourseStatus());

        course = courseService.getById(100L).orElse(null);
        Assert.assertNull(course);
    }
}
