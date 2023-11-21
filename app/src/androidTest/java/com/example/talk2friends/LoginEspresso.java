package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import junit.framework.AssertionFailedError;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginEspresso {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRuleLogin =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRuleSignUp =
            new ActivityScenarioRule<>(SignUpActivity.class);

//    @Rule
//    public IntentsTestRule<LoginActivity> mLoginActivityActivityTestRule =
//            new IntentsTestRule<>(LoginActivity.class);

    public void main(String[] args) {
        TryLogin_empty();
        TryLogin();
    }

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.talk2friends", appContext.getPackageName());
    }

    @Test
    public void TryLogin_empty() {
        // TODO: 1. empty email and password

        int emailInputID = R.id.emailInput;
        onView(withId(emailInputID))
                .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());

        int passwordInputID = R.id.passwordInput;
        onView(withId(passwordInputID))
                .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());

        int loginButtonID = R.id.loginButton;
        onView(withId(loginButtonID))
                .perform(ViewActions.click());

        // expected error text
        String expectedErrorMessage = "Incorrect password or email!\\nPlease try again.";
        onView(withId(R.id.incorrectLoginText)).check(matches(withText(expectedErrorMessage)));
    }



    @Test
    public void TryLogin() {


        // try signing up, then logging in with the same credentials
        int emailInputID2 = R.id.emailInput;
        onView(withId(emailInputID2))
                .perform(ViewActions.typeText("testUser@usc.edu"), ViewActions.closeSoftKeyboard());

        int passwordInputID2 = R.id.passwordInput;
        onView(withId(passwordInputID2))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());

        int loginButtonID2 = R.id.loginButton;
        onView(withId(loginButtonID2))
                .perform(ViewActions.click());

        try {
            // check that we're on the MeetingsActivity
            intended(hasComponent(MeetingsActivity.class.getName()));
        } catch (AssertionFailedError e) {
            System.out.println("Login failed: " + e.getMessage());
            assert(false);
        }



    }


}