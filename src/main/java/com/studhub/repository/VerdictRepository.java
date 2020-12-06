package com.studhub.repository;

import com.studhub.entity.Verdict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerdictRepository extends JpaRepository<Verdict, Long> {

}
