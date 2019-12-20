package com.apploidxxx.heliosbackend.rest.model;

import com.apploidxxx.heliosbackend.rest.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    private List<Map<String, String>> queues = new ArrayList<>();
    private List<Map<String, String>> queuesMember = new ArrayList<>();
    private List<Map<String, String>> groupsMember = new ArrayList<>();

    private List<QueueShortInfo> favorites = new ArrayList<>();

    private User user;

    private List<Map<String, String>> swapRequestsIn = new ArrayList<>();

    private List<Map<String, String>> swapRequestsOut = new ArrayList<>();
}
