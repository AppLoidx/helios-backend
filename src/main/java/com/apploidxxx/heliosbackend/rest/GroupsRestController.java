package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.model.GroupsModel;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/groups")
public class GroupsRestController {

    private final UserManager userManager;

    public GroupsRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public GroupsModel getGroup(
            @CookieValue("session") String session,
            @RequestParam("group_name") String queueName
    ){
        User user = this.userManager.getUser(session);

        return new Request().get("groups", GroupsModel.class,
                "access_token", user.getUserToken().getAccessToken(),
                "group_name", queueName).getBody();
    }

    @PostMapping
    public void createNewGroup(
            @CookieValue("session") String session,

            @RequestParam("group_name") String queueName,
            @RequestParam("fullname") String fullname,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "password", required = false) String password
    ){
        User user = this.userManager.getUser(session);

        new Request().post("groups", Object.class,
                "access_token", user.getUserToken().getAccessToken(),
                "group_name", queueName,
                "fullname", fullname,
                "description", description,
                "password", password);
    }

    @PutMapping
    public void putNewUserToGroup(
            @CookieValue("session") String session,

            @RequestParam("group_name") String queueName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "username", required = false) String username
    ){
        User user = this.userManager.getUser(session);

        Request.put("groups", GroupsModel.class,
                "access_token", user.getUserToken().getAccessToken(),
                "group_name", queueName,
                "password", password,
                "username", username);
    }

    @DeleteMapping
    public GroupsModel deleteGroup(
            @CookieValue("session") String session,
            @RequestParam("group_name") String queueName
    ){
        User user = this.userManager.getUser(session);

        return new Request().get("groups", GroupsModel.class,
                "access_token", user.getUserToken().getAccessToken(),
                "group_name", queueName).getBody();
    }

}
