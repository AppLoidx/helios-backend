package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GenerationType;
import java.util.List;
import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Queue extends WebModel{
    @JsonProperty("creation_date")
    private String creationDate;
    @JsonProperty("generation_type")
    private GenerationType generationType;
    private String password;
    private String name;
    private String fullname;
    private Set<User> members;
    @JsonProperty("super_users")
    private Set<User> superUsers;
    @JsonProperty("queue_sequence")
    private List<Long> queueSequence;
    private String description;
    private Set<QueueNotice> notifications;
}
