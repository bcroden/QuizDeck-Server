package com.quizdeck.model.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class User{

    @Id
    private String id;

    private String userName;
    private String hashedPassword;
    private String saltSeed;

    private String email;

    private Date signUp;

    private List<String> labels;

    @PersistenceConstructor
    public User(String userName, String hashedPassword, String saltSeed, String email, Date signUp) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.saltSeed = saltSeed;
        this.email = email;
        this.signUp = signUp;
    }

    @Override
    public String toString(){
        return String.format("User[id='%s', username='%s']", id, userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSaltSeed() {
        return saltSeed;
    }

    public void setSaltSeed(String saltSeed) {
        this.saltSeed = saltSeed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public Date getSignUp() {
        return signUp;
    }

    public void setSignUp(Date signUp) {
        this.signUp = signUp;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) { this.labels = labels; }

    public String getId() {
        return id;
    }
}
