package com.apploidxxx.heliosbackend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    public UserSocialData() {
    }

    public UserSocialData(Long vkId, String telegramId, Long userId) {

        this.vkId = vkId;
        this.telegramId = telegramId;
        this.userHeliosId = userId;
    }

    @Column
    private Long vkId;

    @Column
    private String telegramId;

    @Column
    private Long userHeliosId;

    public UserSocialData addVkId(Long vkId) {
        this.vkId = vkId;
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
