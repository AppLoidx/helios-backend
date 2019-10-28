package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueNotice implements Comparable<QueueNotice> {
    private String user;
    private String message;
    private Long id;
    private Date creationDate;

    @Override
    public int compareTo(QueueNotice o) {
        return (int) (o.creationDate.getTime() - this.creationDate.getTime());
    }
}
