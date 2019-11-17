package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.model.UserSettings;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/settings/{username}")
public class SettingsRestController {
    private final UserManager userManager;

    public SettingsRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping(produces = "application/json")
    public Object get(
            @PathVariable("username") String username,
            @CookieValue("session") String session,
            HttpServletResponse response) {

        User user = userManager.getUser(session);
        ResponseEntity<UserSettings> responseEntity = new Request().get("settings/" + username, UserSettings.class,
                "access_token", user.getUserToken().getAccessToken());

        response.setStatus(responseEntity.getStatusCode().value());
        return responseEntity.getBody();

    }

    @PutMapping
    public Object put(@PathVariable("username") String username,

                      @CookieValue("session") String session,
                      @RequestParam("property") String property,
                      @RequestParam("value") String value
    ) {

        User user = userManager.getUser(session);

        Request.put("settings/" + username, UserSettings.class,
                "access_token", user.getUserToken().getAccessToken(),
                "property", property,
                "value", value);
        return null;
    }
}
