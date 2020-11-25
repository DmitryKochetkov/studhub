package com.studhub.service;

import com.studhub.dto.LessonDto;
import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LessonServiceTests {
    @Autowired
    private LessonService lessonService;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(lessonService);
    }

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

    @Test
    public void testCreateLesson() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setCourseId(1L);
        lessonDto.setStartDateTime(LocalDateTime.of(2020, 12, 31, 15, 0));
        lessonService.createLesson(lessonDto);
    }
}
