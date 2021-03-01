package com.studhub.dto;

import com.studhub.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;

//@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class HomeworkStatDtoItemTests {
    @Test
    public void testHomeWorkStatDtoItemConstructor() {
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

        HomeworkProblem homeworkProblem = new HomeworkProblem();
        ShortAnswerProblem problem = new ShortAnswerProblem();
        problem.setFormulation("Find a sum of 2 and 3.");
        problem.setCorrectAnswer("5");

        homeworkProblem.setProblem(problem);
        homeworkProblem.setHomework(homework);
        homeworkProblem.setRequired(true);
        homeworkProblem.setMaxAttempts(100);
        homeworkProblem.setSubmissions(new ArrayList<>());

        ArrayList<HomeworkProblem> problems = new ArrayList<>();
        problems.add(homeworkProblem);

        homework.setProblems(problems);

        Course course = new Course();
        course.setId(1L);
        homework.setCourse(course);

        HomeworkStatDtoItem homeworkStatDtoItem = new HomeworkStatDtoItem(homework);

        Assert.assertEquals(deadline, homeworkStatDtoItem.getDate());
        Assert.assertEquals(0.0, homeworkStatDtoItem.getPercentage(), 1e-12);
    }
}
