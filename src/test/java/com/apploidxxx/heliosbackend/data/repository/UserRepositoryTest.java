package com.apploidxxx.heliosbackend.data.repository;

import com.apploidxxx.heliosbackend.HeliosBackendApplication;
import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Arthur Kupriyanov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HeliosBackendApplication.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void userTest() {
        User user1 = new User(new Token("access_token", "refresh_token"));
        userRepository.save(user1);

        User user2 = new User(new Token("access_token", "refresh_token"));
        user2.addSocial().addVkId(123123L).addTelegramId("art222r").addUserHeiosId(12311223L);
        userRepository.save(user2);

        Iterable<User> iter = userRepository.findAll();
        iter.forEach(System.out::println);
    }
}