package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/swap")
public class SwapRestController {
    private final UserManager userManager;

    public SwapRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping(produces = "application/json")
    public Object post(
            HttpServletResponse response,
            @CookieValue("session") String session,

            @RequestParam("target") String target,
            @RequestParam("queue_name") String queueName
    ) {

        User user = userManager.getUser(session);

        ResponseEntity<ErrorMessage> resEnt;
        resEnt = new Request().post("swap", ErrorMessage.class,
                "access_token", user.getUserToken().getAccessToken(),
                "target", target,
                "queue_name", queueName);

        response.setStatus(resEnt.getStatusCode().value());

        if (resEnt.getStatusCode().is2xxSuccessful()) return null;
        else return resEnt.getBody();

    }
}
