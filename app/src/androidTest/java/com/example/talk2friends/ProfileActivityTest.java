package com.example.talk2friends;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;
import android.util.Log;
import com.example.talk2friends.LoginEspresso;

import static org.hamcrest.core.AllOf.allOf;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


    @RunWith(AndroidJUnit4.class)
    public class ProfileActivityTest {

        @Rule
        public ActivityScenarioRule<LoginActivity> activityRuleLogin =
                new ActivityScenarioRule<>(LoginActivity.class);

        public void main(String[] args) {
            //to test them all together, can probably take out the login part?
            tryLoadingProfile();
            tryOpeningEditFriends();
            tryOpeningEditPersonal();
            tryOpeningEditMeetings();
            trySearchFriendsPage();
            tryAddInterestsPage();


        }

//        @Test
//        public void testAgeFieldOnlyAcceptsNumbers() {
//            String testUserEmail = "testUser@usc.edu"; // Replace this with your test user's email
//            System.out.println("Hi I made it in the test");
//            // Create a test user object and set it as the currentUser directly in the test
//            User testUser = new User();
//            testUser.setEmail(testUserEmail); // Set email for the test user
//            // Set other necessary properties for the testUser
//
//            // Launch the ProfileActivity and set the testUser as currentUser
//            Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ProfileActivity.class);
//            intent.putExtra("email", testUserEmail);
//            intent.putExtra("isTestMode", true); // Set the test mode flag
//
//            ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(intent);
//
//            scenario.onActivity(activity -> {
//                activity.setCurrentUser(testUser); // Set the test user directly in the activity
//                Log.d("ProfileActivityTest", "Intent extra 'email': " + activity.getIntent().getStringExtra("email"));
//
//
//                // Rest of your test code for age field validation using Espresso
//                String nonNumericText = "abc";
//                String expectedErrorMessage = "Only numbers are allowed";
//                int editTextId = R.id.ageField; // Replace with your EditText ID
//
//                Espresso.onView(ViewMatchers.withId(editTextId))
//                        .perform(ViewActions.typeText(nonNumericText), ViewActions.closeSoftKeyboard());
//
//                Espresso.onView(ViewMatchers.withId(editTextId))
//                        .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedErrorMessage)));
//            });
//        }

        // Add more test methods as needed

        @Test
        public void tryLoadingProfile(){
            Intents.init();
            //simulate login
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();
            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
               .perform(ViewActions.click());

            int personal_box = R.id.personal_box;

            onView(withId(personal_box))
                    .check(ViewAssertions.matches(isDisplayed()));

        }

        @Test
        public void tryOpeningEditPersonal() {
            Intents.init();
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();

            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
                    .perform(ViewActions.click());

            int editButtonID = R.id.editButton1;
            //Perform action on the editButton within the profile_personal_box
            onView(withId(editButtonID))
                    .perform(ViewActions.click());

            int editPersonal = R.id.edit_personal_box;
            onView(withId(editPersonal))
                    .check(ViewAssertions.matches(isDisplayed()));


        }

//        @Test
//        public void tryChangingNameAndSaving() {
//
//            LoginEspresso esp = new LoginEspresso();
//            esp.TryLogin();
//
//            int profileButtonID = R.id.profileButton;
//            onView(withId(profileButtonID))
//                    .perform(ViewActions.click());
//
//            //opening edit personal box
//            int editButtonID = R.id.editButton1;
//            onView(withId(editButtonID))
//                    .perform(ViewActions.click());
//
//            int nameInputBox = R.id.nameField;
//            System.out.println("Hi");
//            String newText = "Test Name";
//            // Find the second occurrence of nameField
//            onView(allOf(withId(nameInputBox), isDisplayed(), withParent(withId(R.id.edit_personal_box))))
//                    .perform(ViewActions.replaceText(newText), ViewActions.closeSoftKeyboard());
//
//
//
//            int personalSaveButton = R.id.saveButton;
//
//            onView(withId(personalSaveButton))
//                    .perform(ViewActions.click());
//
//            //Reload by clicking profile button again
//            onView(withId(profileButtonID))
//                    .perform(ViewActions.click());
//
//
//        }

        @Test
        public void tryOpeningEditFriends() {
            Intents.init();
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();

            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
                    .perform(ViewActions.click());

            int editButtonID = R.id.editButton2;
            onView(withId(editButtonID))
                    .perform(ViewActions.click());

            int editFriends = R.id.edit_friends_box;
            onView(withId(editFriends))
                    .check(ViewAssertions.matches(isDisplayed()));

        }

        @Test
        public void tryOpeningEditMeetings(){
            Intents.init();
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();

            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
                    .perform(ViewActions.click());

            int editButtonID = R.id.editButton3;
            onView(withId(editButtonID))
                    .perform(ViewActions.scrollTo(), ViewActions.click());

            int editMeetings = R.id.edit_meeting_box;
            onView(withId(editMeetings))
                    .check(ViewAssertions.matches(isDisplayed()));

        }

        @Test
        public void trySearchFriendsPage(){
            Intents.init();
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();

            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
                    .perform(ViewActions.click());

            int searchButtonID = R.id.searchButton;
            onView(withId(searchButtonID))
                    .perform(ViewActions.click());

            int searchBox = R.id.add_friends_page;
            onView(withId(searchBox))
                    .check(ViewAssertions.matches(isDisplayed()));


        }

        @Test
        public void tryAddInterestsPage(){
            Intents.init();
            LoginEspresso esp = new LoginEspresso();
            esp.TryLogin();

            int profileButtonID = R.id.profileButton;
            onView(withId(profileButtonID))
                    .perform(ViewActions.click());

            int searchButtonID = R.id.searchButton;
            onView(withId(searchButtonID))
                    .perform(ViewActions.click());

            int addInterestsID = R.id.addInterests;
            onView(withId(addInterestsID))
                    .perform(ViewActions.click());

            int addInterestsBox = R.id.add_interests_box;
            onView(withId(addInterestsBox))
                    .check(ViewAssertions.matches(isDisplayed()));


        }


    }




