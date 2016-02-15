package Encryption;

import org.bouncycastle.crypto.generators.BCrypt;

import java.security.SecureRandom;

/**
 * Created by Cade on 2/14/2016.
 */
public class PassEncryption {

    public String[] encryptAndSeed(String str){
        byte[] generatedPass = null;
        byte[] salt =  SecureRandom.getSeed(16);
        byte[] bytePass = str.getBytes();
        generatedPass = BCrypt.generate(bytePass, salt, 17);

        String[] result = {generatedPass.toString(), salt.toString()};
        return result;
    }

}
