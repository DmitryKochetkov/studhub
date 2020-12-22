package com.studhub.repository;

import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    Page<Homework> findByCourse(Course course, Pageable pageable);
    List<Homework> findByCourseAndDeadlineIsAfter(Course course, LocalDateTime localDateTime);
}
