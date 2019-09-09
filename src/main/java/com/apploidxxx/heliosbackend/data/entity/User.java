package com.apploidxxx.heliosbackend.data.entity;

import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.Request;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Arthur Kupriyanov
 */
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements Serializable {
    public User() {
    }

    public User(Token token) {
        this.userToken = token;
    }

    public User(Token token, String session, String username) {
        this.username = username;
        this.userToken = token;
        this.session = session;

        setEmailFromAPI();

    }

    public void setEmailFromAPI(){
        try {
            UserModel user = new Request().get("user", UserModel.class,
                    "access_token", this.userToken.getAccessToken()).getBody();
            if (user != null) {
                this.addSocial().addEmail(user.getUser().getContactDetails().getEmail());
            }
        } catch (Exception e){
            LoggerFactory.getLogger(User.class).error("Error while getting user email : ", e);
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    private String session;
    @Column(unique = true, nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Token userToken;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserSocialData userSocialData;

    public UserSocialData addSocial() {
        if (this.userSocialData == null) {
            this.userSocialData = new UserSocialData(this);
        }
        return this.userSocialData;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userToken=" + userToken +
                ", userSocialData=" + userSocialData +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).id.equals(this.id);
        }

        return false;
    }
}
