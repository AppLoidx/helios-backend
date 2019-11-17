package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/search/queue")
public class SearchQueueRestController {

    private final UserManager userManager;

    public SearchQueueRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object searchQueue(
            HttpServletResponse response,
            @CookieValue("session") String session,

            @RequestParam("queue_name") String queueName
    ) {
        User user = userManager.getUser(session);
        ResponseEntity<List> responseEntity =
                new Request().get("search/queue", List.class,
                        "access_token", user.getUserToken().getAccessToken(),
                        "queue_name", queueName);

        response.setStatus(responseEntity.getStatusCode().value());
        return responseEntity.getBody();


    }
}
