package com.apploidxxx.heliosbackend.rest.vk;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.model.vk.VkUser;
import com.apploidxxx.heliosbackend.rest.network.api.vk.OAuthVK;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/vk/receiver")
public class VkOauthReceiver {

    private final UserRepository userRepository;

    public VkOauthReceiver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public @ResponseBody
    Object get(@RequestParam(value = "code", defaultValue = "") String code,
               @RequestParam(value = "error", defaultValue = "") String error,
               @RequestParam(value = "error_description", defaultValue = "") String errorDescription,
               @RequestParam(value = "state", defaultValue = "") String state,
               HttpServletResponse response) throws IOException {
        if (!code.equals("")) {
            Optional<User> userOpt = userRepository.findById(Long.valueOf(state));
            if (!userOpt.isPresent()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ErrorMessage("user_not_found", "Couldn't find user from state");
            }
            User user = userOpt.get();
            VkUser vkUser = new RestTemplate().getForEntity(OAuthVK.getAccessTokenPath(code), VkUser.class, null, null).getBody();//new Request().get(OAuthVK.getAccessTokenPath(code), VkUser.class ).getBody();
            if (vkUser == null) {
                return new ErrorMessage("vk_err", "Error while requesting VK API. Invalid User body");
            }
            user.addSocial().addVkId(Long.valueOf(vkUser.getUserId()));
            userRepository.save(user);
            response.setStatus(200);
            response.sendRedirect("/helios.html#/myprofile");
            return null;
        } else {
            return new ErrorMessage("vk_err", "Error while requesting VK API. Invalid code param");

        }

    }
}
