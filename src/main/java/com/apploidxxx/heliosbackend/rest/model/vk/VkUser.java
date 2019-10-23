package com.apploidxxx.heliosbackend.rest.model.vk;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Arthur Kupriyanov
 */
@Data
@NoArgsConstructor
public class VkUser {

    private String email;

    private String userId;

    private String accessToken;
}
