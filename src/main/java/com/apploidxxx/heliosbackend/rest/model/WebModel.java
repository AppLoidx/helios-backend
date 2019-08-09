package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class WebModel {

    protected String error;
    @JsonProperty("error_description")
    protected String errorDescription;
}
