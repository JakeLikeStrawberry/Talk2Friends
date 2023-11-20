package com.example.talk2friends;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginEspresso {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.talk2friends", appContext.getPackageName());
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