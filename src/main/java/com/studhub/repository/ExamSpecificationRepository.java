package com.studhub.repository;

import com.studhub.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSpecificationRepository extends JpaRepository<Specification, Long> {

}
