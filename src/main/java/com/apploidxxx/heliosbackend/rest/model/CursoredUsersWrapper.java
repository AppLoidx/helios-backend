package com.apploidxxx.heliosbackend.rest.model;

import com.apploidxxx.heliosbackend.rest.model.user.User;
import lombok.Data;

import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class CursoredUsersWrapper {
    private Set<User> cursoredUsers;
}
