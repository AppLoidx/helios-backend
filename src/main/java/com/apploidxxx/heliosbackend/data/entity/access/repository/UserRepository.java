package com.apploidxxx.heliosbackend.data.entity.access.repository;

import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySession(String session);

    Optional<User> findByUserToken(Token token);

    Optional<User> findByUsername(String username);
}


