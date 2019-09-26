package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.config.SourcesConfig;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    @RequestMapping(method = RequestMethod.GET)
    public Object getToken(HttpServletRequest request, HttpServletResponse response,
                           @CookieValue(value = "session", defaultValue = "") String session) throws IOException {

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
        return userRepository.findBySession(session).isPresent();
    }

    private String getRedirectUriToMain(){
        return "/helios.html";
    }

    private String getRedirectUriToAuth(){

        return SourcesConfig.oAuthUri + "?redirect_uri=" + SourcesConfig.DOMAIN + "/api/oauth";
    }
}
