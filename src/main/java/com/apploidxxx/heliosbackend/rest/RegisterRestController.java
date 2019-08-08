package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.rest.util.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping(value = "/api/register")
public class RegisterRestController {
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Object get(HttpServletRequest request, HttpServletResponse response,
               @RequestParam("first_name") String firstName,
               @RequestParam("last_name") String lastName,
               @RequestParam("username") String login,
               @RequestParam("password") String password,
               @RequestParam("email") String email) {
        try {
            return new Request().post("register", String.class,
                    "first_name", firstName,
                    "last_name", lastName,
                    "username", login,
                    "password", password,
                    "email", email).getBody();

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return e.getStatusText();
        }
    }
}
