package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.config.SourcesConfig;
import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@Controller
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public Object getToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue(value = "session", defaultValue = "") String session
    ) throws IOException {

        String redirectUri;

        if (!"".equals(session) && sessionIsValid(session)){
            redirectUri = getRedirectUriToMain();
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            request.getSession(true);
        }
        else redirectUri = getRedirectUriToAuth();

        response.sendRedirect(redirectUri);
        return null;
    }

    private boolean sessionIsValid(String session){
        Optional<User> optUser = userRepository.findBySession(session);
        if (optUser.isPresent()){
            try {
                HttpStatus httpStatus = new Request().get("user", String.class).getStatusCode();
                return httpStatus == HttpStatus.OK;
            } catch (HttpStatusCodeException e){
                return false;
            }
        }
        return false;
    }

    private String getRedirectUriToMain(){
        return "/helios.html";
    }

    private String getRedirectUriToAuth(){

        return SourcesConfig.oAuthUri + "?redirect_uri=" + SourcesConfig.DOMAIN + "/api/oauth";
    }
}
