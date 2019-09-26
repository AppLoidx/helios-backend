package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContactDetails extends WebModel {
    private String email;
    @JsonProperty("vkontakte_id")
    private String vkontakteId;
    private String img;
}
