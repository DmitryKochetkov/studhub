package com.studhub.dto;

import com.studhub.entity.CourseStatus;
import com.studhub.service.CourseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@org.springframework.transaction.annotation.Transactional
public class CourseDtoTests {
    @Autowired
    private CourseService courseService;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(courseService);
    }

    @Test
    @Transactional
    public void testCourseDtoConstructor() {
        CourseDto courseDto = new CourseDto(courseService.getById(1L).orElse(null));
        Assert.assertEquals("Информатика (ЕГЭ)", courseDto.getSubject().getTitle());
        Assert.assertEquals(2L, courseDto.getStudentId().longValue());
        Assert.assertEquals(CourseStatus.ACTIVE, courseDto.getStatus());
    }
}
