package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Arthur Kupriyanov
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSettings extends WebModel{

    private ContactDetails contactDetails;

    private String username;

}