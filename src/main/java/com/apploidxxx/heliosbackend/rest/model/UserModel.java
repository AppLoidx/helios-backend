package com.apploidxxx.heliosbackend.rest.model;

import com.apploidxxx.heliosbackend.rest.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    private List<Map<String , String>> queues;
    private List<Map<String, String>> queuesMember;
    private User user;

    private List<Map<String, String>> swapRequestsIn;

    private List<Map<String, String>> swapRequestsOut;
}
