package com.studhub.dto;

import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

//@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class HomeworkDtoTests {
    @Test
    public void testHomeWorkDtoConstructor() {
        LocalDateTime created = LocalDateTime.of(2020, 11, 23, 12, 30);
        LocalDateTime deadline = LocalDateTime.of(2020, 11, 30, 23, 59);

        Homework homework = new Homework();
        homework.setId(1L);
        homework.setCreated(created);
        homework.setLastModified(created);
        homework.setDeadline(deadline);
        homework.setDescription("Description");

        Course course = new Course();
        course.setId(1L);
        homework.setCourse(course);

        HomeworkDto homeworkDto = new HomeworkDto(homework);
        Assert.assertEquals(1L, homeworkDto.getId().longValue());
        Assert.assertEquals(created, homeworkDto.getCreated());
        Assert.assertEquals(deadline, homeworkDto.getDeadline());
        Assert.assertEquals("Description", homeworkDto.getDescription());
        Assert.assertEquals(course.getId(), homeworkDto.getCourseId().longValue());
    }
}
