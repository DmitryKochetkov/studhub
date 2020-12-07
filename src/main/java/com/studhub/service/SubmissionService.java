package com.studhub.service;

import com.studhub.entity.*;
import com.studhub.payload.SubmissionRequest;
import com.studhub.repository.HomeworkProblemRepository;
import com.studhub.repository.SubmissionRepository;
import com.studhub.repository.VerdictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
        Page<Submission> result = submissionRepository.findByHomeworkProblem_Homework_Id(homeworkId, PageRequest.of(page-1, 10));
        return result;
    }

    public Submission createSubmission(SubmissionRequest submissionRequest) {
        HomeworkProblem homeworkProblem = homeworkProblemRepository.findByHomework_IdAndNumberInHomework(
                submissionRequest.getHomeworkId(),
                submissionRequest.getProblemNumber()).orElseThrow(IllegalArgumentException::new);

        if (homeworkProblem.getHomework().getDeadline().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException();

        if (homeworkProblem.getSubmissions().size() >= homeworkProblem.getMaxAttempts())
            throw new IllegalArgumentException();

        Submission submission = new Submission();
        submission.setAnswer(submissionRequest.getAnswer());
        submission.setHomeworkProblem(homeworkProblem);
        submission.setCreated(LocalDateTime.now());
        submission.setLastModified(LocalDateTime.now());
        submission = submissionRepository.save(submission);

        Submission finalSubmission = submission; //???
        taskExecutor.execute(() -> {
            Verdict verdict = null;
            AbstractProblem abstractProblem = finalSubmission.getHomeworkProblem().getProblem();
            if (abstractProblem instanceof ShortAnswerProblem) {
                ShortAnswerProblem problem = (ShortAnswerProblem) abstractProblem;
                String code = problem.getCorrectAnswer().equals(finalSubmission.getAnswer()) ? "OK" : "WA";
                verdict = verdictRepository.findByCode(code);
            }

            finalSubmission.setVerdict(verdict);
            submissionRepository.save(finalSubmission);
        });
        return submission;
    }
}
