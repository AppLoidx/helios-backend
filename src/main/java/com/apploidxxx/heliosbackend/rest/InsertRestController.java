package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/insert")
public class InsertRestController {
    private final UserManager userManager;

    public InsertRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PutMapping
    public void insertInto(
            @CookieValue("session") String session,
            @RequestParam("old_index") int oldIndex,
            @RequestParam("new_index") int newIndex,
            @RequestParam("queue_name") String queueName
    ){

        Request.put("insert", null,
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "old_index", Integer.toString(oldIndex),
                "new_index", Integer.toString(newIndex),
                "queue_name", queueName);
    }

}
