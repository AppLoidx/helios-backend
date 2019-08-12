package com.apploidxxx.heliosbackend.rest.model.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Arthur Kupriyanov
 */
@Data
@NoArgsConstructor
public class VkUser {

    @JsonProperty("email")
    private String email;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("access_token")
    private String accessToken;
}
