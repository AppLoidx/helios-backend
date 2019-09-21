package com.apploidxxx.heliosbackend.rest.google;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.entity.UserSocialData;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.data.repository.UserSocialDataRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.util.SessionGenerator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/google/oauth")
public class GoogleOAuthRestController {
    private final UserSocialDataRepository userSocialDataRepository;
    private final UserRepository userRepository;

    public GoogleOAuthRestController(UserSocialDataRepository userSocialDataRepository, UserRepository userRepository) {
        this.userSocialDataRepository = userSocialDataRepository;
        this.userRepository = userRepository;
    }

    @PutMapping
    @RequestMapping
    public @ResponseBody Object put(@RequestParam("email") String email, HttpServletResponse response, HttpServletRequest request){
        email = new String(Base64.getDecoder().decode(email));
        System.out.println("EMAIL RECEIVED : " + email);
        Optional<UserSocialData> userSocialDataOpt = userSocialDataRepository.findByEmail(email);
        String sessionId = SessionGenerator.generateSession(email, email);
        if (userSocialDataOpt.isPresent()){
            User user = userSocialDataOpt.get().getUser();
            user.setSession(sessionId);
            Cookie cookie = new Cookie("session", user.getSession());
            cookie.setPath("/api");

            response.addCookie(cookie);
            this.userRepository.save(user);
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            request.getSession(true);
            response.setStatus(200);

            Map<String, String> resp = new HashMap<>();
            resp.put("session", user.getSession());

            return resp;
        } else {
            response.setStatus(404);
            return new ErrorMessage("user_not_found", "User with this email not found");
        }
    }
}
