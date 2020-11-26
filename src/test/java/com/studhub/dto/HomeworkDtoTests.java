package com.studhub.dto;

import com.studhub.entity.AbstractProblem;
import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import com.studhub.entity.ShortAnswerProblem;
import com.studhub.entity.Lesson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class HomeworkDtoTests {
    @Test
    public void testHomeWorkDtoConstructor() {
        LocalDateTime created = LocalDateTime.of(2020, 11, 23, 12, 30);
        LocalDateTime deadline = LocalDateTime.of(2020, 11, 30, 23, 59);

        Lesson lesson = new Lesson();
        lesson.setId(1L);

        Homework homework = new Homework();
        homework.setId(1L);
        homework.setLesson(lesson);
        homework.setCreated(created);
        homework.setLastModified(created);
        homework.setDeadline(deadline);
        homework.setDescription("Description");

        ShortAnswerProblem problem = new ShortAnswerProblem();
        problem.setFormulation("Find a sum of 2 and 3.");
        problem.setRequired(true);
        problem.setAnswer("5");

        ArrayList<AbstractProblem> problems = new ArrayList<>();
        problems.add(problem);

        homework.setProblems(problems);

        Course course = new Course();
        course.setId(1L);
        homework.setCourse(course);

        HomeworkDto homeworkDto = new HomeworkDto(homework);
        Assert.assertEquals(1L, homeworkDto.getId().longValue());
        Assert.assertEquals(created, homeworkDto.getCreated());
        Assert.assertEquals(deadline, homeworkDto.getDeadline());
        Assert.assertEquals("Description", homeworkDto.getDescription());
        Assert.assertEquals(course.getId().longValue(), homeworkDto.getCourseId().longValue());
        Assert.assertEquals(1L, homeworkDto.getTotalProblemsCount().longValue());
        Assert.assertEquals(1L, homeworkDto.getLessonId().longValue());
    }
}
