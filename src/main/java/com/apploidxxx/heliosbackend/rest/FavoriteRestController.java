package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoriteRestController {
    private final UserManager userManager;

    public FavoriteRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object getFavorites(
            @CookieValue("session") String session
    ) {
        return new Request().get("favorite", Object.class,
                "access_token", userManager.getUser(session).getUserToken().getAccessToken())
                .getBody();
    }

    @PutMapping
    public void addToFavorites(
            @CookieValue("session") String session,
            @RequestParam("queue_name") String queueName
    ){
        Request.put("favorite", null,
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "queue_name", queueName
        );
    }

    @DeleteMapping
    public void deleteFromFavorites(
            @CookieValue("session") String session,
            @RequestParam("queue_name") String queueName
    ){
        Request.delete("favorite",
                "access_token", userManager.getUser(session).getUserToken().getAccessToken(),
                "queue_name", queueName
        );
    }

}
