package com.quizdeck.Controllers;

import com.quizdeck.RequestInputObjects.AccountCreationInput;
import com.quizdeck.RequestResponseObjects.AccountCreationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountCreationController {

    public AccountCreationController(){}

    @RequestMapping(value="quizdeck/accountcreation", method= RequestMethod.POST)
    public AccountCreationResponse accountCreateionResponse(@RequestBody AccountCreationInput input){



        return new AccountCreationResponse();
    }

}
