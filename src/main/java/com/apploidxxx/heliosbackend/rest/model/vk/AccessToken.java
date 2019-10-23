package com.apploidxxx.heliosbackend.rest.model.vk;

import com.apploidxxx.heliosbackend.rest.model.WebModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Arthur Kupriyanov
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AccessToken extends WebModel {
    private String accessToken;
    private String expiresIn;
}
