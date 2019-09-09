package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.network.api.helios.OAuthAuthorize;
import com.apploidxxx.heliosbackend.rest.util.Request;
import com.apploidxxx.heliosbackend.rest.util.SessionGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping(value = "/api/register")
public class RegisterRestController {
    private final UserRepository userRepository;

    public RegisterRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Object get(HttpServletResponse response, HttpServletRequest request,
               @RequestParam("first_name") String firstName,
               @RequestParam("last_name") String lastName,
               @RequestParam("username") String login,
               @RequestParam("password") String password,
               @RequestParam("email") String email,
               @RequestParam(value = "group", defaultValue = "") String group) throws IOException {
        try {
            HttpStatus code = new Request().post("register", String.class,
                    "first_name", firstName,
                    "last_name", lastName,
                    "username", login,
                    "password", password,
                    "email", email,
                    "group", group).getStatusCode();

            if (code.is2xxSuccessful()) {
                Token token = OAuthAuthorize.getTokens(login, password);
                String sessionId = SessionGenerator.generateSession(login, password);
                this.userRepository.save(new User(token, sessionId,login));
                request.getSession(true);
                response.addCookie(new Cookie("session", sessionId));
                response.sendRedirect("/helios.html");
                return null;
            } else {
                response.sendRedirect("/external/register.html");
                return null;
            }
        } catch (HttpClientErrorException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getStatusText();
        }
    }
}
