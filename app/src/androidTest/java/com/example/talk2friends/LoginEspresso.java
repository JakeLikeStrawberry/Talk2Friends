package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
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

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRuleLogin =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRuleSignUp =
            new ActivityScenarioRule<>(SignUpActivity.class);

    public void main(String[] args) {

        TryLogin_empty();
        TrySignUp();
        TrySignUpAgain();
        TryLogin();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.talk2friends", appContext.getPackageName());
    }

    @Test
    public void TryLogin_empty() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LoginActivity.class);
        activityRuleLogin.getScenario().onActivity(activity -> {
            activity.setIntent(intent);
        });

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
        String expectedErrorMessage = "Incorrect password or email!\nPlease try again.";
        onView(withId(R.id.incorrectLoginText)).check(matches(withText(expectedErrorMessage)));



        // TODO: 2. email with no @usc.edu

        // TODO: 3. email with @usc.edu

        // TODO: 4. random string that is not an email

        // TODO: 5. if database is empty
    }

    @Test
    public void TryLogin() {
        // try signing up, then logging in with the same credentials

        // Login
        Intent intent2 = new Intent(ApplicationProvider.getApplicationContext(), LoginActivity.class);
        activityRuleLogin.getScenario().onActivity(activity -> {
            activity.setIntent(intent2);
        });

        int emailInputID2 = R.id.emailInput;
        onView(withId(emailInputID2))
                .perform(ViewActions.typeText("testUser@usc.edu"), ViewActions.closeSoftKeyboard());

        int passwordInputID2 = R.id.passwordInput;
        onView(withId(passwordInputID2))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());

        int loginButtonID2 = R.id.loginButton;
        onView(withId(loginButtonID2))
                .perform(ViewActions.click());

        // TODO: check that we're on the MeetingsActivity by checking that it has a "Meetings" TextView or whatever
    }

    @Test
    public void TrySignUpAgain() {
        // TODO: try signing up twice with same email
    }

    @Test
    public void TrySignUp() {

        // Sign up
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), SignUpActivity.class);
        activityRuleSignUp.getScenario().onActivity(activity -> {
            activity.setIntent(intent);
        });
        // TODO: maybe write a function to delete testUser@usc.edu from database
        // TODO: write function to give testUser@usc.edu a freepass through email sending

        int emailInputID = R.id.emailInput;
        onView(withId(emailInputID))
                .perform(ViewActions.typeText("testUser@usc.edu"), ViewActions.closeSoftKeyboard());

        int passwordInputID = R.id.passwordInput;
        onView(withId(passwordInputID))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());

        int signUpButtonID = R.id.signUpButton;
        onView(withId(signUpButtonID))
                .perform(ViewActions.click());

        // TODO: check we're on the ValidationCodeActivity by checking it has a validationInputField TextField
        onView(withId(R.id.validationInput)).check(matches(isDisplayed()));


    }

}