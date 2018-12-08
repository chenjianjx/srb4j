package com.github.chenjianjx.srb4jfullsample.intf.fo.basic;

import org.junit.Test;

import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.PASSWORD_PATTERN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FoConstantsTest {

    @Test
    public void password(){
        //type of chars
        assertTrue("abcdef1".matches(PASSWORD_PATTERN));
        assertTrue("ABCDEF1".matches(PASSWORD_PATTERN));
        assertTrue("12345A".matches(PASSWORD_PATTERN));
        assertTrue("12345a".matches(PASSWORD_PATTERN));
        assertTrue("1a!@#$$%$#".matches(PASSWORD_PATTERN));
        assertFalse("All-letter combination is not valid", "abcdef".matches(PASSWORD_PATTERN));
        assertFalse("All-digit combination is not valid", "123456".matches(PASSWORD_PATTERN));
        assertFalse("Letter and special chars: won't work", "a!@#$%^".matches(PASSWORD_PATTERN));
        assertFalse("Digit and special chars: won't work", "1!@#$%^".matches(PASSWORD_PATTERN));

        //length
        assertFalse("must be at least 6-char long", "abc12".matches(PASSWORD_PATTERN));
        assertTrue("abc123".matches(PASSWORD_PATTERN));
        assertTrue("abcdeabcdeabcde12345".matches(PASSWORD_PATTERN));
        assertFalse("cannot be more than 20 chars","abcdeabcdeabcde123456".matches(PASSWORD_PATTERN));
    }

}