package com.quizdeck.services;

import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.security.SecureRandom;

/**
 * A service designed to handle password hashing and verification.
 *
 * Created by Cade on 2/14/2016.
 */
@Service
public class PasswordHashingService {
    public static int HASH_STRENGTH = 15;

    /**
     * Salts and hashes a password.
     *
     * @param password The password to hash.
     * @return The Base64 encoded hash result.
     */
    public String hashPassword(String password) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBytes = new byte[16];
        secureRandom.nextBytes(saltBytes);

        return getHash(password, saltBytes);
    }

    /**
     * Compares a plaintext password with a Base64 encoded salted and hashed password.
     *
     * @param password The plaintext password.
     * @param saltedHash The Base64 encoded salted and hashed password.
     * @return True if the passwords match, false if otherwise.
     */
    public boolean checkPassword(String password, String saltedHash) {
        String salt = saltedHash.substring(0, 22);

        byte[] saltBytes = Base64Utils.decodeFromString(salt);

        return getHash(password, saltBytes).equals(saltedHash);
    }

    /**
     * Salts and hashes a password into a Base64 encoded string.
     *
     * Prepends the salt to the hash result for use in future comparisons.
     *
     * @param password The plaintext password.
     * @param saltBytes The 16 byte salt.
     * @return The Base64 encoded salted password.
     */
    private String getHash(String password, byte[] saltBytes) {
        byte[] passBytes = password.getBytes();
        byte[] hashBytes = BCrypt.generate(passBytes, saltBytes, HASH_STRENGTH);

        String salt = Base64Utils.encodeToString(saltBytes);
        String hash = Base64Utils.encodeToString(hashBytes);

        return salt + hash;
    }
}
