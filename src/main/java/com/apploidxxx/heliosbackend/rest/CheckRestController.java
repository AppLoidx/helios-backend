package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/check")
public class CheckRestController {

    @GetMapping(produces = "application/json")
    public Object get(
            @RequestParam("check") String check,
            @RequestParam(value = "queue_name", defaultValue = "") String queueName,
            @RequestParam(value = "username", defaultValue = "") String username) {

        return new Request().get("check", Object.class,
                "check", check,
                "queue_name", queueName,
                "username", username).getBody();

    }
}
