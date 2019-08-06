package com.apploidxxx.heliosbackend.rest.util;

import com.apploidxxx.heliosbackend.HeliosBackendApplication;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

/**
 * @author Arthur Kupriyanov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HeliosBackendApplication.class)
public class UserManagerTest {
    @Inject
    UserRepository userRepository;

    @Test
    public void getUserBySession() {
        try {
            System.out.println(new UserManager(userRepository).getUser("session"));
        } catch (UserNotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
    }

}