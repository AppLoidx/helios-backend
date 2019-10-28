package com.apploidxxx.heliosbackend.data.entity.access.repository;

import com.apploidxxx.heliosbackend.data.entity.UserSocialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
public interface UserSocialDataRepository extends JpaRepository<UserSocialData, Long> {
    @Override
    Optional<UserSocialData> findById(Long aLong);

    Optional<UserSocialData> findByEmail(String email);
}
