package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebModel {
    @JsonProperty("error_message")
    protected String error;
    protected String errorDescription;
}
