package com.github.chenjianjx.srb4jfullsample.impl.common;

import java.security.SecureRandom;

public class ImplHelper {


    /**
     * a common user error
     * @param source
     * @return
     */
    public static String pleaseSocialLoginTip(String source) {
        return String.format("%s user please login with %s", source, source);
    }

    public static String generateRandomDigitCode() {
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int randNum = random.nextInt(10);
            sb.append(randNum);
        }
        return sb.toString();
    }
}
