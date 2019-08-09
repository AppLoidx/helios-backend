package com.apploidxxx.heliosbackend.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContactDetails extends WebModel {
    private String email;
    private String vkontakteId;
}
