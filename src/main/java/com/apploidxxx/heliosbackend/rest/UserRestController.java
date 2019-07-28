package com.apploidxxx.heliosbackend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(@RequestParam("type") String type) {
        return "This is response";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser() {
        return "200";
    }
}
