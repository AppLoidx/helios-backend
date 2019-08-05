package com.apploidxxx.heliosbackend.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Arthur Kupriyanov
 */
@Setter
@Getter
@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    public User() {
    }

    public User(Token token) {
        this.userToken = token;
    }

    public User(Token token, String session) {
        this.userToken = token;
        this.session = session;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String session;

    @OneToOne(cascade = CascadeType.ALL)
    private Token userToken;

    @OneToOne(cascade = CascadeType.ALL)
    private UserSocialData userSocialData = new UserSocialData();

    public UserSocialData addSocial() {
        return userSocialData;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userToken=" + userToken +
                ", userSocialData=" + userSocialData +
                '}';
    }
}
