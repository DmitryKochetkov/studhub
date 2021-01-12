package com.studhub.service;

import com.studhub.entity.ExamSpecification;
import com.studhub.repository.ExamSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamSpecificationService {
    @Autowired
    private ExamSpecificationRepository examSpecificationRepository;

    public Optional<ExamSpecification> getById(Long id) {
        return examSpecificationRepository.findById(id);
    }
}
