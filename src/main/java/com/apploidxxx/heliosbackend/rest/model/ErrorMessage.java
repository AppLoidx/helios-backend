package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author Arthur Kupriyanov
 */
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    @JsonAlias("error")
    public String error;
    @JsonProperty("error_description")
    public String errorDescription;
}
