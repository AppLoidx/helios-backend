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

    public UserSocialData(User user) {
        this.user = user;
    }

    @Column
    private Long vkId;

    @Column
    private String telegramId;

    @Column
    private String email;

    public UserSocialData addEmail(String email) {
        this.email = email;
        return this;
    }

    public UserSocialData addVkId(Long vkId) {
        this.vkId = vkId;
        return this;
    }

    public UserSocialData addTelegramId(String telegramId) {
        this.telegramId = telegramId;
        return this;
    }


}
