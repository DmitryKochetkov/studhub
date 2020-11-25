package com.studhub.service;

import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import com.studhub.payload.CreateLessonRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

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
    public void testCreateLessonOK() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setCourseId(1L);
        request.setTopic("Test topic");
        request.setCourseId(1L);
        request.setStartDate(LocalDate.of(2020, 12, 31));
        request.setStartTime(LocalTime.of(15, 0));
        lessonService.createLesson(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateLessonBadRequest1() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setCourseId(1L);
        request.setTopic("Test topic");
        request.setCourseId(null);
        request.setStartDate(LocalDate.of(2020, 12, 31));
        request.setStartTime(LocalTime.of(15, 0));
        lessonService.createLesson(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateLessonBadRequest2() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setCourseId(1L);
        request.setTopic("Test topic");
        request.setCourseId(100L);
        request.setStartDate(LocalDate.of(2020, 12, 31));
        request.setStartTime(LocalTime.of(15, 0));
        lessonService.createLesson(request);
    }
}
