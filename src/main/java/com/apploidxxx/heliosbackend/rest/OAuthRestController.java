package com.apploidxxx.heliosbackend.rest;


import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.network.api.helios.OAuthAuthorize;
import com.apploidxxx.heliosbackend.rest.network.api.helios.exception.UnauthorizedException;
import com.apploidxxx.heliosbackend.rest.util.SessionGenerator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping(value = "/api/auth")
public class OAuthRestController {
    private final UserRepository userRepository;

    public OAuthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Object getToken(HttpServletResponse response,
                    @CookieValue(value = "session", defaultValue = "null") String session,
                    @RequestParam(value = "login", defaultValue = "") String login,
                    @RequestParam(value = "password", defaultValue = "") String password) {

        if (login.equals("") || password.equals("")) {
            if (session.equals("null")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return new ErrorMessage("invalid login or password", "Your login or password is invalid. Please, add the GET parameters on your http request");
            } else {
                Optional<User> optUser = userRepository.findBySession(session);
                if (optUser.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    // todo : add authorization to security
                    return null;
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return new ErrorMessage("invalid session", "Your session token is invalid. Please update your session token with new request that has GET parameters for auth");
                }
            }

        } else {
            try {
                Token token = OAuthAuthorize.getTokens(login, password);                // авторизация с helios - api oauth
                String sessionId = SessionGenerator.generateSession(login, password);
                userRepository.save(new User(token, sessionId));
                response.addCookie(new Cookie("session", sessionId));
                response.setStatus(HttpServletResponse.SC_OK);
                // todo : add authorization to security
                return null;

            } catch (UnauthorizedException e) {
                response.setStatus(e.getStatus());
                return e.getErrMsg();
            }
        }

    }
}
