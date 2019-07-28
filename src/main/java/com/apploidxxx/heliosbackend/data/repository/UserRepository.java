package com.apploidxxx.heliosbackend.data.repository;

import com.apploidxxx.heliosbackend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySession(String session);
}
