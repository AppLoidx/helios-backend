package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class User {
    @JsonProperty("contact_details")
    private ContactDetails contactDetails;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("username")
    private String username;
    private Long id;
}
