package com.studhub.repository;

import com.studhub.entity.Course;
import com.studhub.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Page<Submission> findByHomeworkProblem_Homework_Id(Long id, Pageable pageable);
    List<Submission> findAllByHomeworkProblem_Homework_Course(Course course);
}
