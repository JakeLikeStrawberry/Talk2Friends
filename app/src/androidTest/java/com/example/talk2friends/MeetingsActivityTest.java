package com.example.talk2friends;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import android.view.View;
import android.widget.Button;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.talk2friends.LoginEspresso;

import static org.hamcrest.core.AllOf.allOf;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.espresso.intent.Intents;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MeetingsActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRuleLogin =
            new ActivityScenarioRule<>(LoginActivity.class);

    public void main(String[] args) {
        //to test them all together, can probably take out the login part?
        tryLoadingMeetings();
        tryCreateMeetingButton();
        tryMeetingInfoButton();
        tryRSVPToMeetingWhenAlreadyRSVPd();

    }


    @Test
    public void tryLoadingMeetings(){

        Intents.init();

        //simulate login
        LoginEspresso esp = new LoginEspresso();
        esp.TryLogin();
        //re-clicking meetings button to test functionality/reload page
        int meetingsButtonID = R.id.meetingsButton;
        onView(withId(meetingsButtonID))
                .perform(ViewActions.click());
        //choose unique identifier to validate that its on the right page
        int createMeetingButton = R.id.createMeetingButton;

        onView(withId(createMeetingButton))
                .check(ViewAssertions.matches(isDisplayed()));


        //Intents.release();
    }



    @Test
    public void tryCreateMeetingButton(){
        Intents.init();

        //simulate login
        LoginEspresso esp = new LoginEspresso();
        esp.TryLogin();

        //select createMeetingButton
        int createMeetingButton = R.id.createMeetingButton;
        onView(withId(createMeetingButton))
                .perform(ViewActions.click());

        int meetingBox = R.id.transparentGray;
        onView(withId(createMeetingButton))
                .check(ViewAssertions.matches(isDisplayed()));


    }


    @Test
    public void tryMeetingInfoButton(){

        Intents.init();

        //simulate login
        LoginEspresso esp = new LoginEspresso();
        esp.TryLogin();

        //select a meeting
        //using custom matcher to find this meeting for testing purposes since
        //they are dynamically created and have no direct references
        String meetingName = "I'm lonely :(";
        onView(withButtonText(meetingName)).perform(ViewActions.click());

        int meetingBox = R.id.transparentGray;
        onView(withId(meetingBox))
                .check(ViewAssertions.matches(isDisplayed()));


    }

    @Test
    public void tryRSVPToMeetingWhenAlreadyRSVPd() {

        Intents.init();

        //simulate login
        LoginEspresso esp = new LoginEspresso();
        esp.TryLogin();

        //select a meeting
        //using custom matcher to find this meeting for testing purposes since
        //they are dynamically created and have no direct references
        String meetingName = "I'm lonely :(";
        onView(withButtonText(meetingName)).perform(ViewActions.click());

        //find rsvp button
        int rsvpButton = R.id.rsvpButton;
        onView(withId(rsvpButton))
                .perform(ViewActions.click());


    }

    //custom matcher to find a Button with a specific text
    public static Matcher<View> withButtonText(final String buttonText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text: " + buttonText);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof Button)) {
                    return false;
                }
                Button button = (Button) view;
                return buttonText.equals(button.getText().toString());
            }
        };
    }

}





