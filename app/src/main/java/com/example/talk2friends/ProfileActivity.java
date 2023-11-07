package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    // current User
    private User currentUser;

    // topbar
    private ImageButton profileButton;
    private ImageButton meetingsButton;

    // edit buttons
    private ImageButton editButton_personal;
    private ImageButton editButton_friends;
    private ImageButton editButton_meetings;

    //search button
    private ImageButton searchButton_friends;

    // edit personal
    private TextView transparentGray;
    private View edit_personal_box;
    private EditText edit_personal_name;
    private EditText edit_personal_age;
    private EditText edit_personal_affiliation;
    private Spinner edit_personal_typeDropdown;
    private ImageButton edit_personal_saveButton;

    // edit friends
    private View edit_friends_box;
    private ImageButton edit_friends_saveButton;

    //add Friends page
    private View add_friends_page;
    private View search_friends_box;
    private ImageButton send_email_invite;
    private EditText enter_friend_email;
    private View recommend_friends_box;

    //add interests
    private View add_interests_box;
    private Spinner sports_dropdown;
    private Spinner music_dropdown;
    private Spinner reading_dropdown;
    private Spinner exercise_dropdown;
    private Spinner movies_dropdown;
    private Button add_interests_button;
    private ImageButton add_interests_saveButton;

    //edit meetings
    private View edit_meetings_box;
    private ImageButton edit_meetings_saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get logged in user
        String tempEmail = getIntent().getStringExtra("email");
        DatabaseHandler.getUser(tempEmail, new UserCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
                System.out.println("Current user: " + currentUser.getEmail() + ", " + currentUser.getPassword());

                loadPersonal();
                loadFriends();
            }
        });

        // top bar
        profileButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.profileButton);
        meetingsButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.meetingsButton);

        // edit buttons
        editButton_personal = (ImageButton) findViewById(R.id.personal_box).findViewById(R.id.editButton);
        editButton_friends = (ImageButton) findViewById(R.id.friends_box).findViewById(R.id.editButton);
        editButton_meetings = (ImageButton) findViewById(R.id.meetings_box).findViewById(R.id.editButton);

        //search button for friends
        searchButton_friends = (ImageButton) findViewById(R.id.friends_box).findViewById(R.id.searchButton);

        //add friends page
        add_friends_page = (View) findViewById(R.id.add_friends_page);
        search_friends_box = (View) findViewById(R.id.add_friends_page).findViewById(R.id.findFriends);
        recommend_friends_box = (View) findViewById(R.id.add_friends_page).findViewById(R.id.recommendFriends);
        enter_friend_email = (EditText) findViewById(R.id.add_friends_page).findViewById(R.id.emailField);
        add_interests_button = (Button) findViewById(R.id.add_friends_page).findViewById(R.id.addInterests);

        // edit personal
        transparentGray = (TextView) findViewById(R.id.transparentGray);
        edit_personal_box = (View) findViewById(R.id.edit_personal_box);
        edit_personal_saveButton = (ImageButton) findViewById(R.id.edit_personal_box).findViewById(R.id.saveButton);
        edit_personal_name = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.nameField);
        edit_personal_age = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.ageField);
        edit_personal_affiliation = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.affiliationField);
        edit_personal_typeDropdown = (Spinner) findViewById(R.id.edit_personal_box).findViewById(R.id.typeDropdown);

        // edit friends
        edit_friends_box = (View) findViewById(R.id.edit_friends_box);
        edit_friends_saveButton = (ImageButton) findViewById(R.id.edit_friends_box).findViewById(R.id.saveButton);

        //edit meetings
        edit_meetings_box = (View) findViewById(R.id.edit_meeting_box);
        edit_meetings_saveButton = (ImageButton) findViewById(R.id.edit_meeting_box).findViewById(R.id.saveButton);

        //add interests
        add_interests_box = (View) findViewById(R.id.add_interests_box);
        sports_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.sportsDropdown);
        music_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.musicDropdown);
        reading_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.readingDropdown);
        exercise_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.exerciseDropdown);
        movies_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.moviesDropdown);
        add_interests_saveButton = (ImageButton) findViewById(R.id.add_interests_box).findViewById(R.id.saveButton);

//        profileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Utils.switchActivityProfile(ProfileActivity.this, currentUser.getEmail());
//            }
//        });
        meetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.switchActivityMeetings(ProfileActivity.this, currentUser.getEmail());
            }
        });
        editButton_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEditPersonal();
            }
        });
        edit_personal_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePersonal();
                toggleEditPersonal();
            }
        });
        // TODO: make editButton_friends and editButton_meetings do something

        editButton_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { toggleEditFriends(); }
        });

        edit_friends_saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveFriends();
                toggleEditFriends();
            }
        });
        searchButton_friends.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                toggleEditAddFriends();
            }
        });

        editButton_meetings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                toggleEditMeetings();
            }
        });

        edit_meetings_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEditMeetings();
                saveMeetings();
            }
        });

        add_interests_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadInterests();
                toggleEditAddFriends();
                toggleEditAddInterests();

            }
        });
        add_interests_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditAddInterests();
                saveInterests();
            }
        });


        // set dropdown items
        String[] types = new String[]{"international student", "native speaker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        edit_personal_typeDropdown.setAdapter(adapter);

        // set more dropdown items
        String[] answers = new String[]{"yes", "no"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, answers);
        sports_dropdown.setAdapter(adapter2);
        music_dropdown.setAdapter(adapter2);
        reading_dropdown.setAdapter(adapter2);
        exercise_dropdown.setAdapter(adapter2);
        movies_dropdown.setAdapter(adapter2);

    }

    private void toggleEditPersonal () {
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_personal_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_personal_box.setVisibility(View.GONE);
        }

    }

    private void toggleEditAddInterests() {
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            add_interests_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            add_interests_box.setVisibility(View.GONE);
        }
    }
    private void toggleEditFriends () {

        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_friends_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_friends_box.setVisibility(View.GONE);
        }

    }
    private void toggleEditAddFriends () {

        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            add_friends_page.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            add_friends_page.setVisibility(View.GONE);
        }
    }
    private void toggleEditMeetings () {
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_meetings_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_meetings_box.setVisibility(View.GONE);
        }
    }

    private void savePersonal() {
        // save personal info to database
        System.out.println("Saving personal info...");

        // get the editable view
        View editable_data = findViewById(R.id.edit_personal_box);
        TextView edit_name = editable_data.findViewById(R.id.nameField);
        TextView edit_age = editable_data.findViewById(R.id.ageField);
        TextView edit_affiliation = editable_data.findViewById(R.id.affiliationField);
        Spinner edit_type = editable_data.findViewById(R.id.typeDropdown);

        // get new values
        String newName = edit_name.getText().toString();
        String newAge = edit_age.getText().toString();
        String newAffiliation = edit_affiliation.getText().toString();
        String newType = edit_type.getSelectedItem().toString();

        System.out.println("testing program");

        // update values in database
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "name", newName);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "age", newAge);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "affiliation", newAffiliation);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "type", newType);
    }

    private void saveFriends() {
        //saving friends to database

    }
    private void saveMeetings(){
        //saving meetings to database
    }

    private void saveInterests() {

    }

    private void loadInterests(){
        DatabaseHandler.getValue("users", "interests", currentUser.getInterests(), new DataSnapshotCallback() {

            View data = findViewById(R.id.personal_box);
            TextView sports = data.findViewById(R.id.nameField);
            TextView music = data.findViewById(R.id.ageField);
            TextView reading = data.findViewById(R.id.affiliationField);
            TextView exercise = data.findViewById(R.id.typeField);
            TextView email = data.findViewById(R.id.emailField);

            View editable_data = findViewById(R.id.edit_personal_box);
            TextView edit_name = editable_data.findViewById(R.id.nameField);
            TextView edit_age = editable_data.findViewById(R.id.ageField);
            TextView edit_affiliation = editable_data.findViewById(R.id.affiliationField);
            TextView edit_email = editable_data.findViewById(R.id.emailField);




            @Override
            public void onCallback(DataSnapshot data) {

            }
        })
    }

    private void loadPersonal() {
        // get email
        DatabaseHandler.getValue("users", "email", currentUser.getEmail(), new DataSnapshotCallback() {

            View data = findViewById(R.id.personal_box);
            TextView name = data.findViewById(R.id.nameField);
            TextView age = data.findViewById(R.id.ageField);
            TextView affiliation = data.findViewById(R.id.affiliationField);
            TextView type = data.findViewById(R.id.typeField);
            TextView email = data.findViewById(R.id.emailField);

            View editable_data = findViewById(R.id.edit_personal_box);
            TextView edit_name = editable_data.findViewById(R.id.nameField);
            TextView edit_age = editable_data.findViewById(R.id.ageField);
            TextView edit_affiliation = editable_data.findViewById(R.id.affiliationField);
            TextView edit_email = editable_data.findViewById(R.id.emailField);


            @Override
            public void onCallback(DataSnapshot data) {
                if (data != null) {
                    name.setText("Data recieved");
                    // set name equal to the user name
                    Object nameValue = data.child("name").getValue();
                    Object ageValue = data.child("age").getValue();
                    Object affiliationValue = data.child("affiliation").getValue();
                    Object typeValue = data.child("type").getValue();

                    // set name
                    if (nameValue != null) {
                        name.setText(nameValue.toString());
                        edit_name.setText(nameValue.toString());
                    } else {
                        name.setText("");
                        edit_name.setText("");
                    }

                    // set age
                    if (ageValue != null) {
                        age.setText(ageValue.toString());
                        edit_age.setText(ageValue.toString());
                    } else {
                        age.setText("");
                        edit_age.setText("");
                    }


                    // set affiliation
                    if (affiliationValue != null) {
                        affiliation.setText(affiliationValue.toString());
                        edit_affiliation.setText(affiliationValue.toString());
                    } else {
                        affiliation.setText("");
                        edit_affiliation.setText("");
                    }


                    // set type
                    if (typeValue != null) {
                        type.setText(typeValue.toString());

                    } else {
                        type.setText("");

                    }

                    if(currentUser.getEmail() != null)
                    {

                        email.setText(currentUser.getEmail());
                        edit_email.setText(currentUser.getEmail());
                    }

                    return;
                }

                name.setText("error loading name");

                // if null or password wrong
                System.out.println("Login failed. Either email or password is incorrect.");



            }
        });

    }

    private void loadFriends()
    {

        // get email
        DatabaseHandler.getValue("users", "email", currentUser.getEmail(), new DataSnapshotCallback() {

            View data = findViewById(R.id.friends_box);


            View editable_data = findViewById(R.id.edit_friends_box);



            @Override
            public void onCallback(DataSnapshot data) {
                if (data != null) {
                    Object friendsData = data.child("friends").getValue();

                    Map<String, String> friendsList;

                    if (friendsData == null) {
                        friendsList = new HashMap<>(); // Create an empty dictionary
                        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "friends", friendsList);
                    } else {
                        // Assuming friendsData is in a format that can be converted to a Map<String, String>
                        friendsList = (Map<String, String>) friendsData;
                    }

                    System.out.println(friendsList);

                    ConstraintLayout parentLayout = findViewById(R.id.friends_box);
                    LinearLayout childLayout = findViewById(R.id.friends_lin_box);

                    ConstraintLayout parentLayout2 = findViewById(R.id.edit_friends_box);
                    LinearLayout childLayout2 = findViewById(R.id.friends_lin_box_2);

                    for (int i = 0; i < friendsList.size(); i++) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) friendsList.entrySet().toArray()[i];
                        String friendName = entry.getKey();
                        String friendEmail = entry.getValue();

                        // Create a new instance of the layout for each friend
                        View dynamicLayout = getLayoutInflater().inflate(R.layout.dynamic_button_no_x, null);
                        View dynamicLayout2 = getLayoutInflater().inflate(R.layout.dynamic_button, null);

                        // Set Button text
                        Button nameButton = dynamicLayout.findViewById(R.id.nameButton);
                        nameButton.setText(friendName);

                        Button nameButton2 = dynamicLayout2.findViewById(R.id.nameButton);
                        nameButton2.setText(friendName);

                        // Assign unique IDs to the buttons
                        nameButton.setId(View.generateViewId());
                        nameButton2.setId(View.generateViewId());

                        // Add the dynamic layout to the parent layout
                        childLayout.addView(dynamicLayout);
                        childLayout2.addView(dynamicLayout2);

                        // Store the buttons in a data structure for later access
                        // Example: List<Button> buttonList = new ArrayList<>();
                        // buttonList.add(nameButton);
                        // buttonList.add(nameButton2);

                        ImageButton x = dynamicLayout2.findViewById(R.id.crossButton);

                        x.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                System.out.print("Clickable clicked");
                                // Handle button click
                                String friendName = nameButton2.getText().toString();

                                // Assuming friendsList is a Map<String, String>
                                friendsList.remove(friendName);

                                // Remove the button from the UI
                                childLayout2.removeView(v);

                                // remove from database
                                DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "friends", friendsList);                            }
                        });
                    }



                    return;
                }

            }

        });



    }





}
