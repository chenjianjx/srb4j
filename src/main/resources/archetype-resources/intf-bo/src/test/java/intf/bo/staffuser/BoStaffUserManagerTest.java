package com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoStaffUserManagerTest {

    @Test
    public void testUsernamePattern() {
        assertFalse("".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertFalse("a".matches(BoStaffUserManager.USER_NAME_REGEX)); //too short
        assertTrue("abc".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertTrue("abc123".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertTrue("abc_123".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertFalse("123abc".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertTrue("a123456789".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertFalse("a1234567890".matches(BoStaffUserManager.USER_NAME_REGEX)); //too long
        assertFalse("Abc123".matches(BoStaffUserManager.USER_NAME_REGEX));
        assertFalse("A-123".matches(BoStaffUserManager.USER_NAME_REGEX));
    }
}