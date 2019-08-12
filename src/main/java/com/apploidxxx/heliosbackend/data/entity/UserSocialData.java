package com.apploidxxx.heliosbackend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Arthur Kupriyanov
 */
@Getter
@Setter
@Entity
public class UserSocialData implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    public UserSocialData() {
    }

    @Column
    private Long vkId;

    @Column
    private String telegramId;

    @Column
    private Long userHeliosId;

    @Column
    private String vkAccessToken;

    public UserSocialData addVkId(Long vkId) {
        this.vkId = vkId;
        return this;
    }

    public UserSocialData addVkAccessToken(String acccessToken) {
        this.vkAccessToken = acccessToken;
        return this;
    }

    public UserSocialData addTelegramId(String telegramId) {
        this.telegramId = telegramId;
        return this;
    }

    public UserSocialData addUserHeiosId(Long userHeiosId) {
        this.userHeliosId = userHeiosId;
        return this;
    }
}
