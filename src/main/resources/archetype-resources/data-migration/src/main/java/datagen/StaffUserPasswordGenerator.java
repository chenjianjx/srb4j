package com.github.chenjianjx.srb4jfullsample.datagen;


import com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser.BoStaffUserManager;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyCodecUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class StaffUserPasswordGenerator {

    public static final char[] PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()".toCharArray();

    public static void main(String[] args) {
        System.out.println("Ready to generate password for a new staff user.");
        System.out.println("Enter the username (a combination of 3 to 10 lower-cased letters, numbers and '_', starting with a letter): ");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        String username = null;
        while (username == null) {
            username = StringUtils.trimToNull(scanner.nextLine());
            if (username == null) {
                continue;
            }

            if (!username.matches(BoStaffUserManager.USER_NAME_REGEX)) {
                System.err.println("The username is not valid.");
                System.out.println();
                username = null;
            }
        }


        int passwordSize = RandomUtils.nextInt(6, 13);
        String password = RandomStringUtils.random(passwordSize, PASSWORD_CHARS);
        String encodedPassword = MyCodecUtils.encodePasswordLikeDjango(password);
        System.out.println();
        System.out.println("Username is: " + username);
        System.out.println("Password is: " + password);
        System.out.println();
        System.out.println("To create this staff user, run the following sql: ");
        System.out.println();
        String sqlTemplate = "insert into StaffUser(username, password, createdAt, createdBy) "
                + "values ('%s', '%s', now(), 'staff user generator');";
        System.out.println();
        System.out.println(String.format(sqlTemplate, username, encodedPassword));
        System.out.println();

    }
}
