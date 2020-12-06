package com.studhub.service;

import com.studhub.entity.Verdict;
import com.studhub.repository.VerdictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerdictService {
    @Autowired
    private VerdictRepository verdictRepository;

    public List<Verdict> getAll() {
        return verdictRepository.findAll();
    }
}
