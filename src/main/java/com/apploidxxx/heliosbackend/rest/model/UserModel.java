package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class UserModel {
    private List<Map<String , String>> queues;
    @JsonProperty("queues_member")
    private List<Map<String, String>> queuesMember;
    private User user;
}
