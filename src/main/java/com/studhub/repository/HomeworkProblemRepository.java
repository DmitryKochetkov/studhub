package com.studhub.repository;

import com.studhub.entity.Homework;
import com.studhub.entity.HomeworkProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkProblemRepository extends JpaRepository<HomeworkProblem, Long> {
    HomeworkProblem findByHomeworkAndNumberInHomework(Homework homework, Integer numberInHomework);
}