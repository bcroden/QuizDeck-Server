package com.quizdeck.model.inputs;

import com.quizdeck.model.database.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 3/11/2016.
 */
@Getter
@Setter
public class UserEditInput {

    private User editedUser;

}
