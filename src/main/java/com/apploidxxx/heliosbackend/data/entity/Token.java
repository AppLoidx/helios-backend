package com.apploidxxx.heliosbackend.data.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Getter
@Setter
public class Token implements Serializable {
    @OneToOne(mappedBy = "userToken")
    private User user;

    @Id
    @GeneratedValue
    private Long id;

    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("refresh_token")
    private String refreshToken;

    public Token() {
    }

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token aToken = (Token) obj;
            return aToken.id.equals(this.id);
        }
        return false;
    }
}
