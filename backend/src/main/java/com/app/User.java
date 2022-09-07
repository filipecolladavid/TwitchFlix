package com.app;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = encryptPassword(password);
    }

    private String encryptPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(passwordToHash.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public Boolean checkPassword(String p2) {
        return password.equals(p2);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}