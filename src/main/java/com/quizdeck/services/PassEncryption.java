package com.quizdeck.services;

import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * Created by Cade on 2/14/2016.
 */
@Service
public class PassEncryption {

    public String[] encryptAndSeed(String str){
        byte[] generatedPass = null;
        byte[] salt =  SecureRandom.getSeed(16);
        byte[] bytePass = str.getBytes();
        generatedPass = BCrypt.generate(bytePass, salt, 17);

        String[] result = {generatedPass.toString(), salt.toString()};
        return result;
    }

    public String encryptUsingSaltSeed(String pass, String seed){
        byte[] generatedPass = BCrypt.generate(pass.getBytes(), seed.getBytes(), 17);

        return generatedPass.toString();
    }

}
