package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document
public class User{

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String hashedPassword;
    private String saltSeed;

    private String email;

    private Date signUp;

    private List<String> labels;

    @PersistenceConstructor
    public User(String userName, String hashedPassword, String email, Date signUp) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.signUp = signUp;
    }

    @Override
    public String toString(){
        return String.format("User[id='%s', username='%s']", id, userName);
    }

}
