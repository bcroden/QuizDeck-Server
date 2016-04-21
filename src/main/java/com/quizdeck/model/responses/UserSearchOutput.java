package com.quizdeck.model.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 4/21/2016.
 */
@Getter
@Setter
public class UserSearchOutput {

    String userName;


    public UserSearchOutput(String userName){
        this.userName = userName;
    }
}
