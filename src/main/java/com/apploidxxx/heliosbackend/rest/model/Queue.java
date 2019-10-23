package com.apploidxxx.heliosbackend.rest.model;

import com.apploidxxx.heliosbackend.rest.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GenerationType;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Queue extends WebModel{
    private String creationDate;
    private GenerationType generationType;
    private String password;
    private String name;
    private String fullname;
    private Set<User> members;
    private Set<User> superUsers;
    private List<Long> queueSequence;
    private String description;
    private TreeSet<QueueNotice> notifications;
}
