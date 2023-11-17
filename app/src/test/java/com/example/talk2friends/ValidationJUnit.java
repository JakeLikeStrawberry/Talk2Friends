package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ValidationJUnit {

    public static void main(String[] args) {
        // run all login tests
        System.out.println("Running Login tests...");

        LoginJUnit loginJUnit = new LoginJUnit();
    }

    @Test
    public void TryGenerateValidationCode() {
        // test if generateValidationCode() function...

        // TODO: 1. returns a 4 digit code

        // TODO: 2. returns alphanumeric

        // TODO: 3. is all uppercase

        // TODO: 4. is a string?
    }

}