package com.apploidxxx.heliosbackend.rest.vk;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.network.api.vk.OAuthVK;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("api/vk/link")
public class VkOauthRestController {
    private final UserRepository userRepository;

    public VkOauthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public @ResponseBody
    Object get(@CookieValue("session") String session,
               HttpServletResponse response) throws IOException {
        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        }
        response.sendRedirect(OAuthVK.getCodeTokenPath(String.valueOf(user.getId())));
        return null;
    }

}
