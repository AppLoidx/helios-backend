package com.apploidxxx.heliosbackend.rest;


import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.SessionGenerator;
import com.apploidxxx.heliosbackend.rest.util.UserRestGetter;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping(value = "/api/oauth")
public class OAuthRestController {
    private final UserRepository userRepository;

    public OAuthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Object getToken(HttpServletResponse response, HttpServletRequest request,
                    @RequestParam(value = "authorization_code", defaultValue = "") String authorizationCode) {

        Token token;
        try {
            token = getToken(authorizationCode);
        } catch (HttpStatusCodeException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }

        if (token == null) return tokenIsInvalidServerError(response);

        UserModel userModel = UserRestGetter.getUser(token);
        if (userModel == null) return tokenIsInvalidServerError(response);

        User user = userRepository.findByUsername(userModel.getUser().getUsername()).orElse(null);

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        request.getSession(true);

        try {
            if (user == null) return setNewUser(response, userModel.getUser(), token);
            else return updateOldUser(response, user, token);

        } catch (IOException e) {
            response.setStatus(500);
            return new ErrorMessage("internal_server_error", "Error occurred when trying redirect: " + e.getMessage());
        }


    }

    private Token getToken(String code) throws HttpStatusCodeException {
        return new Request().get("oauth", Token.class, "authorization_code", code).getBody();
    }

    private ErrorMessage tokenIsInvalidServerError(HttpServletResponse response) {
        response.setStatus(500);
        return new ErrorMessage("invalid_token", "Responded invalid tokens from Helios API");
    }

    private Object setNewUser(HttpServletResponse response, com.apploidxxx.heliosbackend.rest.model.User uninitializedUser, Token token) throws IOException {
        User user = generateNewUser(token, uninitializedUser);
        userRepository.save(user);
        return saveUserSessionAndRedirectToMainPage(response, user);
    }

    private Object updateOldUser(HttpServletResponse response, User user, Token token) throws IOException {
        user.setUserToken(token);
        userRepository.save(user);
        return saveUserSessionAndRedirectToMainPage(response, user);
    }

    private User generateNewUser(Token token, com.apploidxxx.heliosbackend.rest.model.User user) {

        return new User(token, SessionGenerator.generateSession(user.getUsername(), user.getFirstName()), user.getUsername());

    }

    private Object saveUserSessionAndRedirectToMainPage(HttpServletResponse response, User user) throws IOException {
        setSessionCookie(response, user);
        response.setStatus(HttpServletResponse.SC_OK);
        return redirectToMainPage(response);
    }

    private void setSessionCookie(HttpServletResponse response, User user) {
        Cookie c = new Cookie("session", user.getSession());
        response.addCookie(c);
    }

    private Object redirectToMainPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/helios.html");
        return null;
    }


}
