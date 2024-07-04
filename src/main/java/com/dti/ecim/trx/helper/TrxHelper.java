package com.dti.ecim.trx.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class TrxHelper {
    public static String generateTixCode(String name) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(Instant.now().toString().getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(name.substring(0, 3));
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.substring(0, 9).toUpperCase();
    }
}
