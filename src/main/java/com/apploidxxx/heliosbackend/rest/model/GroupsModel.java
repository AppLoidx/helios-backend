package com.apploidxxx.heliosbackend.rest.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author Arthur Kupriyanov
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupsModel {

    TreeSet<Map<String, String>> users;
    TreeSet<Map<String, String>> superUsers;
    private Group group;

    @Data
    public static class Group {
        private Long id;
        private String name;
        private String fullname;
        private String description;

    }

}
