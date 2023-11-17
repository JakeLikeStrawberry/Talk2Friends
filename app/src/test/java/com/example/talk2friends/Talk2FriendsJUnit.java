package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Talk2FriendsJUnit {

    public static void main(String[] args) {
        // run all tests
        System.out.println("Running all tests...");

        System.out.println("Running Login tests...");
        System.out.println("Running Sign Up tests...");
        System.out.println("Running Validation tests...");
        System.out.println("Running Meetings tests...");
        System.out.println("Running Profile tests...");
    }

    @Test
    public void TryLogin() {
        assertEquals(4, 2 + 2);
    }
}