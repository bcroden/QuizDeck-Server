package com.quizdeck.model.database;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class User{

    @Id
    private String id;

    private String userName;
    private String password;

    private String email;
    private String fName;
    private String lName;

    private Date signUp;

    public String getUsername() {
        return userName;
    }

    @Override
    public String toString(){
        return String.format("User[id='%s', username='%s']", id, userName);
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Date getSignUp() {
        return signUp;
    }

    public void setSignUp(Date signUp) {
        this.signUp = signUp;
    }
}
