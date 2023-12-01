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
public class SignUpEspresso {

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
        TrySignUp();
        TrySignUpAgain();
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
    public void TrySignUpAgain() {
        // TODO: try signing up again with existing email "testUser@usc.edu"
        // testUser@usc.edu gets a freepass through SignUp and ValidationCode :)
        // username: "testUser@usc.edu"
        // password:
            // "newSignUp" if want new sign up --> otherwise, testUser@usc.edu already exists in database
        // hash: "hash"
        // validation code: "1234"

        DoSignUp("testUser@usc.edu", "oldSignUp");

        // check we're still on the SignUpActivity
        try {
            intended(hasComponent(SignUpActivity.class.getName()));
        } catch (AssertionFailedError e) {
            System.out.println("Validation failed: " + e.getMessage());
            assert(false);
        }

        // expected error text
        String expectedErrorMessage = "Email already registered!\\nPlease log in.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));

    }

    @Test
    public void TrySignUp() {
        // testUser@usc.edu gets a freepass through SignUp and ValidationCode :)
        // username: "testUser@usc.edu"
        // password:
        // "newSignUp" if want new sign up
        // "oldSignUp" if want to use existing account
        // hash: "hash"
        // validation code: "1234"

        DoSignUp("testUser@usc.edu", "newSignUp");

        // check we're on the ValidationCodeActivity
        try {
            // check that we're on the MeetingsActivity
            intended(hasComponent(ValidationCodeActivity.class.getName()));
        } catch (AssertionFailedError e) {
            System.out.println("Sign up failed: " + e.getMessage());
            assert(false);
        }

        // input validation code "1234" for testUser
        int validationCodeInputID = R.id.validationInput;
        onView(withId(validationCodeInputID))
                .perform(ViewActions.typeText("1234"), ViewActions.closeSoftKeyboard());

        // click submit
        int submitButtonID = R.id.confirmButton;
        onView(withId(submitButtonID))
                .perform(ViewActions.click());

        // check we're on the Required
        try {
            intended(hasComponent(RequiredProfileActivity.class.getName()), times(1));
        } catch (AssertionFailedError e) {
            System.out.println("Validation failed: " + e.getMessage());
            assert(false);
        }

    }

    @Test
    public void TrySignUp_EmptyEmail() {
        // TODO: 1. empty email

        DoSignUp("", "123456");

        // expected error text
        String expectedErrorMessage = "Please add an email!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TrySignUp_EmptyPassword() {
        // TODO: 1. empty password

        DoSignUp("testUser@usc.edu", "");

        // expected error text
        String expectedErrorMessage = "Please add a password!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TrySignUp_NonUSCEmail() {
        // TODO: 2. email with no @usc.edu

        DoSignUp("testUser@gmail.com", "123456");

        // expected error text
        String expectedErrorMessage = "Please add a valid USC email!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TryLogin_NonEmail() {
        // TODO: 2. email string that is not an email (no @)

        DoSignUp("testUser.usc.edu", "123456");

        // expected error text
        String expectedErrorMessage = "Please add a valid USC email!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TryLogin_NoUsername() {
        // TODO: 2. email string that is just @usc.edu

        DoSignUp("@usc.edu", "123456");

        // expected error text
        String expectedErrorMessage = "Please add an email!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TryLogin_LongPass() {
        // TODO: password that is > 30 characters

        DoSignUp("testUser@usc.edu", "123456789_123456789_123456789_1");

        // expected error text
        String expectedErrorMessage = "Password is too long (> 30)!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TryLogin_ShortPass() {
        // TODO: password that is < 6 characters

        DoSignUp("testUser@usc.edu", "12345");

        // expected error text
        String expectedErrorMessage = "Password is too short (< 6)!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    @Test
    public void TryLogin_LongEmail() {
        // email length > 64 + 8

        // input 65 + 8 chars
        String email = "";
        for (int i = 0; i < 65; i++) {
            email += "a";
        }
        email += "@usc.edu";
        DoSignUp(email, "123456");

        // expected error text
        String expectedErrorMessage = "Email is too long (> 72)!\\nPlease try again.";
        onView(withId(R.id.incorrectSignUpText)).check(matches(withText(expectedErrorMessage)));
    }

    public void DoSignUp(String email, String password) {
        int signUpButtonID = R.id.signUpButton;
        onView(withId(signUpButtonID))
                .perform(ViewActions.click());

        int emailInputID = R.id.emailInput;
        onView(withId(emailInputID))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        int passwordInputID = R.id.passwordInput;
        onView(withId(passwordInputID))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        int signUpButtonID2 = R.id.signUpButton;
        onView(withId(signUpButtonID2))
                .perform(ViewActions.click());
    }

}