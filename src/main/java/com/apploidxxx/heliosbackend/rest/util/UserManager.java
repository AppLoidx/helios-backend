package com.apploidxxx.heliosbackend.rest.util;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.entity.access.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public User getUser(String session, String redirectUri, HttpServletResponse response) throws IOException, UserNotFoundException {
        try {
            return getUser(session);
        } catch (UserNotFoundException e) {
            response.sendRedirect(redirectUri);
            throw new UserNotFoundException();
        }
    }
}
