package com.studhub.service;

import com.studhub.entity.Submission;
import com.studhub.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public Page<Submission> getByHomeworkId(Long homeworkId, Integer page) {
        Page<Submission> result = submissionRepository.findByHomeworkProblem_Homework_Id(homeworkId, PageRequest.of(page-1, 10));
        return result;
    }
}
