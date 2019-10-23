package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badge {
    private String text;
    private String color;
}
