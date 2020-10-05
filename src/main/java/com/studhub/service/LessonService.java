package com.studhub.service;

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

//    public Lesson createLesson(CreateLessonRequest request) {
//        Lesson lesson = new Lesson();
//        lesson.setStartDate(request.getStartDate());
//        lesson.setTopic(request.getTopic());
//        lesson.setCreated(new Date());
//        lesson.setLastModified(new Date());
//        lesson.setCourse(courseRepository.findById(request.getCourseId()).orElse(null)); //TODO: handle null
//        return lessonRepository.save(lesson);
//    }
}
