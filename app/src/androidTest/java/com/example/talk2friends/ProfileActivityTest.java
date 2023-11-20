package com.example.talk2friends;

import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

    @RunWith(AndroidJUnit4.class)
    public class ProfileActivityTest {

        @Test
        public void testAgeFieldOnlyAcceptsNumbers() {
            String testUserEmail = "testUser@usc.edu"; // Replace this with your test user's email
            System.out.println("Hi I made it in the test");
            // Create a test user object and set it as the currentUser directly in the test
            User testUser = new User();
            testUser.setEmail(testUserEmail); // Set email for the test user
            // Set other necessary properties for the testUser

            // Launch the ProfileActivity and set the testUser as currentUser
            Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ProfileActivity.class);
            intent.putExtra("email", testUserEmail);
            intent.putExtra("isTestMode", true); // Set the test mode flag

            ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(intent);

            scenario.onActivity(activity -> {
                activity.setCurrentUser(testUser); // Set the test user directly in the activity
                Log.d("ProfileActivityTest", "Intent extra 'email': " + activity.getIntent().getStringExtra("email"));


                // Rest of your test code for age field validation using Espresso
                String nonNumericText = "abc";
                String expectedErrorMessage = "Only numbers are allowed";
                int editTextId = R.id.ageField; // Replace with your EditText ID

                Espresso.onView(ViewMatchers.withId(editTextId))
                        .perform(ViewActions.typeText(nonNumericText), ViewActions.closeSoftKeyboard());

                Espresso.onView(ViewMatchers.withId(editTextId))
                        .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedErrorMessage)));
            });
        }

        // Add more test methods as needed
    }
