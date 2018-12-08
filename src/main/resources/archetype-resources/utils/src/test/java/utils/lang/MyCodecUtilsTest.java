package com.github.chenjianjx.srb4jfullsample.utils.lang;


import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MyCodecUtilsTest {

    @Test
    public void testIsPasswordDjangoMatches() throws Exception {
        assertTrue(MyCodecUtils.isPasswordDjangoMatches("dhde8212", "pbkdf2_sha256$10548$qZFRFfsx7E$GJtb+sRQipEJnu+ORrwZe8xQE1SQV4pXijFnVl0G5CI="));
        assertTrue(MyCodecUtils.isPasswordDjangoMatches("Hello@Beckham", "pbkdf2_sha256$10191$ZXQ1yh5fWD$K0PXJG3Ro8aG4Vu6sgszszrRssngLVicyLI/ax0hQtA="));

    }
}