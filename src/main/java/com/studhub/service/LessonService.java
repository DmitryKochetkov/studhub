package com.studhub.service;

import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import com.studhub.payload.CreateLessonRequest;
import com.studhub.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseService courseService;

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    public Lesson createLesson(CreateLessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setStartDateTime(LocalDateTime.of(request.getStartDate(), request.getStartTime()));
        lesson.setTopic(request.getTopic());
        lesson.setStatus(LessonStatus.SCHEDULED);

        Date date = new Date();
        lesson.setCreated(date);
        lesson.setLastModified(date);

        Long courseId = request.getCourseId();
        if (courseId == null)
            throw new IllegalArgumentException();
        lesson.setCourse(courseService.getById(courseId).orElseThrow(IllegalArgumentException::new));
        return lessonRepository.save(lesson);
    }
}
