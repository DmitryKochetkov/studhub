package com.studhub.repository;

import com.studhub.entity.ExamSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSpecificationRepository extends JpaRepository<ExamSpecification, Long> {

}
