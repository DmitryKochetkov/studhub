package com.studhub.repository;

import com.studhub.entity.Homework;
import com.studhub.entity.HomeworkProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeworkProblemRepository extends JpaRepository<HomeworkProblem, Long> {
    HomeworkProblem findByHomeworkAndNumberInHomework(Homework homework, Integer numberInHomework);
    Optional<HomeworkProblem> findByHomework_IdAndNumberInHomework(Long homeworkId, Integer numberInHomework);
}