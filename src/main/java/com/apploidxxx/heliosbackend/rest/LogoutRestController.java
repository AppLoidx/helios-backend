package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.access.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("api/logout")
public class LogoutRestController {
    private final UserRepository userRepository;

    public LogoutRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Object logout(
            @CookieValue(value = "session", required = false) String session,
            HttpServletResponse response
    ) throws IOException {

        if (session == null) response.sendRedirect("/api/auth");

        new UserManager(userRepository).getUser(session);

        response.setStatus(308);
        invalidateSessionCookie(response);

        response.sendRedirect("/api/auth");
        return null;
    }

    private void invalidateSessionCookie(HttpServletResponse response) {
        Cookie c = new Cookie("session", null);
        c.setMaxAge(0);
        response.addCookie(c);
    }
}
