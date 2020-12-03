package com.studhub.service;

import com.studhub.entity.Homework;
import com.studhub.entity.HomeworkProblem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HomeworkServiceTests {
    @Autowired
    private HomeworkService homeworkService;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(homeworkService);
    }

    @Test
    public void testGetById() {
        Homework homework = homeworkService.getById(1L).orElse(null);
        Assert.assertNotNull(homework);
        Assert.assertEquals(1, homework.getId().longValue());
        Assert.assertEquals("petr", homework.getCourse().getStudent().getUsername());
        Assert.assertEquals(3, homework.getProblems().size());
    }

    @Test
    public void getProblemInHomework() {
        for (int problemId = 1; problemId <= 3; problemId++) {
            Optional<HomeworkProblem> abstractProblem = homeworkService.getProblemInHomework(1L, problemId);
            Assert.assertTrue(abstractProblem.isPresent());
        }
    }
}
