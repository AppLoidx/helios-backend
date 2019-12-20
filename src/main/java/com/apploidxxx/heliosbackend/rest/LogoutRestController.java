package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("api/logout")
public class LogoutRestController {
    private final UserManager userManager;

    public LogoutRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object logout(
            @CookieValue(value = "session", required = false) String session,
            HttpServletResponse response, HttpServletRequest request
    ) throws IOException {

        if (session == null) response.sendRedirect("/api/auth");

        User user = userManager.getUser(session);
        user.setSession(null);
        userManager.saveUser(user);

        response.setStatus(308);
        invalidateSessionCookie(request, response);

        response.sendRedirect("/api/auth");
        return null;
    }

    private void invalidateSessionCookie(HttpServletRequest request, HttpServletResponse response) {


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.asList(cookies).forEach(c -> {
                        c.setMaxAge(0);
                        response.addCookie(c);
                    });
        }

    }
}
