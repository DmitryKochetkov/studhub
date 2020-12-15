package com.studhub.repository;

import com.studhub.entity.Student;
import com.studhub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends AbstractUserRepository<Student> {
    Page<Student> findAll(Pageable pageable);
    Student findByUsername(String nickname);
    Page<User> findUsersByFollowingContains(User user, Pageable pageable); //find all users who follow user
    Page<User> findUsersByFollowersContains(User user, Pageable pageable); //find all users who are followed by user
}
