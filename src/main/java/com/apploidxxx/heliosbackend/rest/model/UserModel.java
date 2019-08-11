package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class UserModel {
    private List<String[]> queues;
    @JsonProperty("queues_member")
    private List<String[]> queuesMember;
    private User user;
}
