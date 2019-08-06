package com.apploidxxx.heliosbackend.rest.util;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@Component
public class UserManager {
    private final UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String session) throws UserNotFoundException {
        Optional<User> optUser = userRepository.findBySession(session);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new UserNotFoundException();
        }
    }
}
