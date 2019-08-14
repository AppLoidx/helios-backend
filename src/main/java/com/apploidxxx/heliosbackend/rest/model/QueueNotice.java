package com.apploidxxx.heliosbackend.rest.model;

import lombok.Data;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class QueueNotice {
    private String user;
    private String message;
    private Long id;
}
