package com.studhub.service;

import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LessonServiceTests {
    @Autowired
    private LessonService lessonService;

    @Test
    public void testGetById() {
        Lesson lesson = lessonService.getById(1L);
        Assert.assertNotNull(lesson);
        Assert.assertEquals(1L, lesson.getId().longValue());
        Assert.assertEquals(LessonStatus.SCHEDULED, lesson.getStatus());
        Assert.assertEquals("Topic 1", lesson.getTopic());

        lesson = lessonService.getById(100L);
        Assert.assertNull(lesson);
    }
}
