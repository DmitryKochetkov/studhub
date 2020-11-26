package com.studhub.service;

import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import com.studhub.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepository;

    public Page<Homework> getAllHomeworkInCourse(Course course, Integer page) {
        Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Order.desc("created")));
        return homeworkRepository.findByCourse(course, pageable);
    }

}
