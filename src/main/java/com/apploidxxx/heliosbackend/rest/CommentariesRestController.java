package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RequestMapping("/api/commentaries")
@RestController
public class CommentariesRestController {

    private final UserManager userManager;

    public CommentariesRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object get(
            @CookieValue("session") String session,
            @RequestParam("username") String username
    ){
        return new Request().get("commentaries", Object.class,
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "username", username).getBody();
    }

    @PutMapping
    public void put(
            @CookieValue("session") String session,
            @RequestParam("text") String text,
            @RequestParam("username") String username,
            @RequestParam("type") String type
    ){

        Request.put("commentaries", null,
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "username", username,
                "text", text,
                "type", type);

    }

    @DeleteMapping
    public void delete(
            @CookieValue("session") String session,
            @RequestParam("commentary_id") Long commentaryId
    ) {

        Request.delete("commentaries",
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "username", commentaryId.toString());

    }
}
