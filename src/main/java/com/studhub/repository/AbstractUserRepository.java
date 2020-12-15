package com.studhub.repository;

import com.studhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractUserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findById(Long id);
}
