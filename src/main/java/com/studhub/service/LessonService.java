package com.studhub.service;

import com.studhub.dto.LessonDto;
import com.studhub.entity.Lesson;
import com.studhub.repository.CourseRepository;
import com.studhub.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    public Lesson createLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setStartDate(lessonDto.getStartDate());
        lesson.setTopic(lessonDto.getTopic());
        lesson.setStatus(lessonDto.getStatus());
        Date date = new Date();
        lesson.setCreated(date);
        lesson.setLastModified(date);
        lesson.setCourse(courseRepository.findById(1L).orElse(null)); //TODO: handle null
        return lessonRepository.save(lesson);
    }
}
