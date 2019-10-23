package com.apploidxxx.heliosbackend.rest.model.user;

import com.apploidxxx.heliosbackend.rest.model.ContactDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private ContactDetails contactDetails;
    private String firstName;
    private String lastName;
    private String username;
    private String groupName;
    private UserType userType;

    private Long id;


}
