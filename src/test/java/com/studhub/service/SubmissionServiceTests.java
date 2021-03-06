package com.studhub.service;

import com.studhub.entity.HomeworkProblem;
import com.studhub.entity.Submission;
import com.studhub.payload.SubmissionRequest;
import com.studhub.repository.HomeworkProblemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Transactional
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SubmissionServiceTests {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private HomeworkProblemRepository homeworkProblemRepository;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(submissionService);
    }

    @Test
    public void testGetByHomeworkId() {
        Page<Submission> page = submissionService.getByHomeworkId(1L, 1);
        Assert.assertNotNull(page);
        Assert.assertEquals(10, page.getSize());
        Assert.assertEquals(5, page.getContent().size());
    }

    @Test
    public void testCreateSubmissionOK() {
        SubmissionRequest request = new SubmissionRequest();
        HomeworkProblem homeworkProblem = homeworkProblemRepository.findByHomework_IdAndNumberInHomework(1L, 2).orElseThrow(IllegalArgumentException::new);
        request.setAnswer("1");
        Submission submission = submissionService.createSubmission(homeworkProblem, request);
        Assert.assertNotNull(submission);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSubmissionFailedWhenNoAttemptsLeft() {
        SubmissionRequest request = new SubmissionRequest();
        HomeworkProblem homeworkProblem = homeworkProblemRepository.findByHomework_IdAndNumberInHomework(1L, 1).orElseThrow(IllegalArgumentException::new);
        request.setAnswer("1");
        submissionService.createSubmission(homeworkProblem, request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSubmissionFailedWhenDeadlineIsExpired() {
        SubmissionRequest request = new SubmissionRequest();
        HomeworkProblem homeworkProblem = homeworkProblemRepository.findByHomework_IdAndNumberInHomework(2L, 2).orElseThrow(IllegalArgumentException::new);
        request.setAnswer("1");
        submissionService.createSubmission(homeworkProblem, request);
    }
}
