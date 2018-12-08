package com.github.chenjianjx.srb4jfullsample.utils.lang;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class MyCodecUtils {

    public static final String DJANGO_HASH_ALGORITHM_NAME = "pbkdf2_sha256";

    /**
     * https://docs.djangoproject.com/en/2.1/topics/auth/passwords/#how-django-stores-passwords
     * With dynamic salt your don't have to store different static salts for each environment.
     * <p/>
     * The code is adapted from https://gist.github.com/lukaszb/1af1bd4233326e37a8a0
     */
    public static String encodePasswordLikeDjango(String clearPassword) {

        String salt = RandomStringUtils.randomAlphanumeric(10);
        int iterations =  RandomUtils.nextInt(10000, 12000);
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = hashLikeDjango(clearPassword, salt, iterations);
        return String.format("%s$%d$%s$%s", DJANGO_HASH_ALGORITHM_NAME, iterations, salt, hash);

    }

    public static boolean isPasswordDjangoMatches(String clearPassword, String encodedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and HASH
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        String algorithmName = parts[0];
        if (!algorithmName.equals(DJANGO_HASH_ALGORITHM_NAME)) {
            return false;
        }
        int iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String storedHash = parts[3];

        String hashUsingStoredSetup = hashLikeDjango(clearPassword, salt, iterations);

        return storedHash.equals(hashUsingStoredSetup);
    }


    private static String hashLikeDjango(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;

        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
            SecretKey secret = keyFactory.generateSecret(keySpec);

            byte[] rawHash = secret.getEncoded();
            byte[] hashBase64 = Base64.getEncoder().encode(rawHash);
            return new String(hashBase64);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }

    }
}
