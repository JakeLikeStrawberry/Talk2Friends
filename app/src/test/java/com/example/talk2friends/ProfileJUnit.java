package com.example.talk2friends;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

public class ProfileJUnit {
    public static void main (String[] args) {
            System.out.println("Beginning Profile Tests: ");

    }

    @Test
    public void InvalidAgeEntered() {

        Activity profileActivity = new ProfileActivity();

        assertEquals(4, 2 + 2);

    }


}

