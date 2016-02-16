package com.quizdeck.services;

import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by Cade on 2/14/2016.
 */
@Service
public class PassEncryption {

    public ArrayList<byte[]> encryptAndSeed(String str){
        byte[] generatedPass = null;
        byte[] salt =  SecureRandom.getSeed(16);
        byte[] bytePass = str.getBytes();
        generatedPass = BCrypt.generate(bytePass, salt, 15);

        ArrayList<byte[]> result = new ArrayList();
        result.add(generatedPass);
        result.add(salt);
        return result;
    }

    public String encryptUsingSaltSeed(byte[] pass, byte[] salt){
        byte[] generatedPass = BCrypt.generate(pass, salt, 15);

        return Base64.getEncoder().encodeToString(generatedPass);
    }

}
