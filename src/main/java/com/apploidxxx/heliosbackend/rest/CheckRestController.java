package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/check")
public class CheckRestController {
    @GetMapping(produces = "application/json")
    public @ResponseBody
    Object get(@RequestParam("check") String check,
               @RequestParam(value = "queue_name", defaultValue = "") String queueName,
               @RequestParam(value = "username", defaultValue = "") String username) {
        try {
            return new Request().get("check", String.class, "check", check,
                    "queue_name", queueName, "username", username);
        } catch (HttpStatusCodeException e) {
            return e.getResponseBodyAsString();
        }
    }
}
