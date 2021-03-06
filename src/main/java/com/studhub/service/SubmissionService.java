package com.studhub.service;

import com.studhub.entity.*;
import com.studhub.payload.SubmissionRequest;
import com.studhub.repository.HomeworkProblemRepository;
import com.studhub.repository.SubmissionRepository;
import com.studhub.repository.VerdictRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SubmissionService {
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private VerdictRepository verdictRepository;

    @Autowired
    private HomeworkProblemRepository homeworkProblemRepository;

    public Page<Submission> getByHomeworkId(Long homeworkId, Integer page) {
        Page<Submission> result = submissionRepository.findByHomeworkProblem_Homework_Id(homeworkId, PageRequest.of(page-1, 10, Sort.by("created").descending()));
        return result;
    }

    public Submission createSubmission(HomeworkProblem homeworkProblem, SubmissionRequest submissionRequest) {
        if (homeworkProblem.getHomework().getDeadline().isBefore(LocalDateTime.now())) {
            log.info("Submission was not created: deadline passed");
            throw new IllegalArgumentException();
        }

        if (homeworkProblem.getSubmissions().size() >= homeworkProblem.getMaxAttempts()) {
            log.info("Submission was not created: attempts used");
            throw new IllegalArgumentException();
        }

        Submission submission = new Submission();
        submission.setAnswer(submissionRequest.getAnswer());
        submission.setHomeworkProblem(homeworkProblem);
        submission.setCreated(LocalDateTime.now());
        submission.setLastModified(LocalDateTime.now());
        submission = submissionRepository.save(submission);
        log.info("Created " + submission.toString());

        taskExecutor.execute(new Evaluator(submission.getId(), submissionRepository, verdictRepository));

        return submission;
    }

    public List<Submission> getByCourse(Course course) {
        return submissionRepository.findAllByHomeworkProblem_Homework_Course(course);
    }
}

@Slf4j
class Evaluator implements Runnable {
    Long submissionId;

    private SubmissionRepository submissionRepository;
    private VerdictRepository verdictRepository;

    public Evaluator(Long submissionId, SubmissionRepository submissionRepository, VerdictRepository verdictRepository) {
        this.submissionId = submissionId;
        this.submissionRepository = submissionRepository;
        this.verdictRepository = verdictRepository;
    }

    @Override
    public void run() {
        Submission finalSubmission = submissionRepository.findById(submissionId).orElseThrow(); //???
        Verdict verdict = null;
        String code = null;
        try {
            AbstractProblem abstractProblem = finalSubmission.getHomeworkProblem().getProblem();
            if (abstractProblem instanceof ShortAnswerProblem) {
                ShortAnswerProblem problem = (ShortAnswerProblem) abstractProblem;
                code = problem.getCorrectAnswer().equals(finalSubmission.getAnswer()) ? "OK" : "WA";
            } else if (abstractProblem instanceof ChoiceProblem) {
                ChoiceProblem problem = (ChoiceProblem) abstractProblem;
                code = problem.getAnswers().stream().anyMatch(
                        answer -> answer.getText().equals(finalSubmission.getAnswer()) && answer.isCorrect()) ? "OK" : "WA";
            }

            verdict = verdictRepository.findByCode(code);
            finalSubmission.setVerdict(verdict);
        } catch (RuntimeException e) {
            finalSubmission.setVerdict(verdictRepository.findByCode("SE"));
        }

        if (verdict == null)
            finalSubmission.setVerdict(verdictRepository.findByCode("SE"));

        log.info(finalSubmission.toString() + " evaluated with verdict " + verdict.toString());
        submissionRepository.save(finalSubmission);
    }
}