package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

import android.app.Activity;
import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginJUnit {

    public static void main(String[] args) {
        // run all login tests
        System.out.println("Running Login tests...");

        LoginJUnit loginJUnit = new LoginJUnit();
    }

    @Test
    public void TryGenerateValidationCode() {
        // import LoginActivity
        Activity loginActivity = new LoginActivity();

        // test if generateValidationCode() function...

        // TODO: 1. returns a 4 digit code
        loginActivity.generateValidationCode();

        // TODO: 2. returns alphanumeric

        // TODO: 3. is all uppercase

        // TODO: 4. is a string?
    }

    @Test
    public void TryCheckSignUpErrors() {
        // test if checkSignUpErrors() function...

        // TODO: 1. returns false for empty password

        // TODO: 2. returns false for empty email

        // TODO: 3. returns false for emoji in email?

        // TODO: 4. returns false for emoji in password?

        // TODO: 5. limits password length to 30 characters

        // TODO: 6. limits email length to 64 + 8 characters, including @usc.edu

    }
}