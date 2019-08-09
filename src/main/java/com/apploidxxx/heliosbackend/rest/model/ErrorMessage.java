package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ErrorMessage extends WebModel {
    @JsonAlias("error")
    public String error;
    @JsonProperty("error_description")
    public String errorDescription;
}
