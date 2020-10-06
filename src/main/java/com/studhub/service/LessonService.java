package com.studhub.service;

import com.studhub.dto.LessonDto;
import com.studhub.entity.Course;
import com.studhub.entity.Lesson;
import com.studhub.payload.CreateLessonRequest;
import com.studhub.repository.CourseRepository;
import com.studhub.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    CourseRepository courseRepository;

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public List<Lesson> getAll() { return lessonRepository.findAll(); }

    public Lesson createLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setStartDate(lessonDto.getStartDate());
        lesson.setTopic(lessonDto.getTopic());
        Date date = new Date();
        lesson.setCreated(date);
        lesson.setLastModified(date);
        lesson.setCourse(courseRepository.findById(1L).orElse(null)); //TODO: handle null
        return lessonRepository.save(lesson);
    }
}
