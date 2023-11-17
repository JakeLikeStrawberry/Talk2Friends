package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SignUpJUnit {

    public static void main(String[] args) {
        // run all signup tests
        System.out.println("Running SignUp tests...");

        LoginJUnit loginJUnit = new LoginJUnit();

    }

    @Test
    public void TryLogin() {
        // test if tryLogin() function works with...

        // TODO: 1. empty email and password

        // TODO: 2. email with no @usc.edu

        // TODO: 3. email with @usc.edu

        // TODO: 4. random string that is not an email
    }

    @Test
    public void ValidateLogin() {
        // test if validateLogin() function works with...

        // TODO: 1. empty email and password

        // TODO: 2. if database is empty
    }
}