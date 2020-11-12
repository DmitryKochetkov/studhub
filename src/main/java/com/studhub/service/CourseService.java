package com.studhub.service;

import com.studhub.entity.Course;
import com.studhub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course getById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
}
