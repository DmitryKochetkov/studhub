package com.studhub.service;

import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import com.studhub.entity.HomeworkProblem;
import com.studhub.enums.BusinessPeriod;
import com.studhub.repository.HomeworkProblemRepository;
import com.studhub.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private HomeworkProblemRepository homeworkProblemRepository;

    public Page<Homework> getAllHomeworkInCourse(Course course, Integer page) {
        Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Order.desc("deadline")));
        return homeworkRepository.findByCourse(course, pageable);
    }

    public Optional<Homework> getById(Long id) {
        return homeworkRepository.findById(id);
    }

    public Optional<HomeworkProblem> getProblemInHomework(Long homeworkId, Integer problemNumber) {
        Homework homework = getById(homeworkId).orElse(null);
        if (homework == null)
            return Optional.empty();
        return Optional.ofNullable(homeworkProblemRepository.findByHomeworkAndNumberInHomework(homework, problemNumber));
    }

    public List<Homework> getAllHomeworkInCourseByBusinessPeriod(Course course, BusinessPeriod businessPeriod) {
        LocalDateTime localDateTime = businessPeriod.getFirstDate();
        return homeworkRepository.findByCourseAndDeadlineIsBetween(course, localDateTime, LocalDateTime.now());
    }
}
