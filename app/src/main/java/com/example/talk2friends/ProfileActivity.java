package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {

    // current User
    private User currentUser;
    private boolean isTestMode = false;

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
    private ImageButton send_email_button;
    private EditText enter_friend_email;
    private View recommend_friends_box;
    private ImageButton add_friend_button;

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
        if (getIntent().hasExtra("isTestMode")) {
            isTestMode = getIntent().getBooleanExtra("isTestMode", false);
        }
        System.out.println("Hi I made it here in the activity");


        DatabaseHandler.getUser(tempEmail, new UserCallback() {
            @Override
            public void onCallback(User user) {
                System.out.println("Before assigning user");
                currentUser = user;
                System.out.println("Woah I made it this far");
                if(isTestMode){
                    System.out.println("went into true");
                    loadPersonal();
                }
                else{
                    System.out.println("Went into else");
                    //currentUser.setEmail("testUser@usc.edu");
                    //System.out.println("Current user: " + currentUser.getEmail() + ", " + currentUser.getPassword());
                    loadPersonal();
                    loadFriends();
                    System.out.println("loading rec friends");
                    loadRecommendedFriends();
                    loadMeetings();
                }
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
        send_email_button = (ImageButton) findViewById(R.id.add_friends_page).findViewById(R.id.sendEmailButton);
        add_friend_button = (ImageButton) findViewById(R.id.add_friends_page).findViewById(R.id.addFriendButton);

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

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPersonal();
                loadFriends();
                loadMeetings();
                loadRecommendedFriends();
            }
        });

        meetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.switchActivityMeetings(ProfileActivity.this, currentUser.getEmail());
            }
        });

        transparentGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopups();
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

        editButton_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { toggleEditFriends(); }
        });

        edit_friends_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopups();
            }
        });

        searchButton_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEditAddFriends();
            }
        });

        send_email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySendInvitationEmail();
            }
        });

        add_friend_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tryAddFriend();
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
                // TODO:
                loadMeetings();
                closePopups();
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
                loadRecommendedFriends();
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

    private void trySendInvitationEmail() {
        // check that user exists already
        DatabaseHandler.getUser(enter_friend_email.getText().toString() + "@usc.edu", new UserCallback() {
            @Override
            public void onCallback(User data) {
                if (data != null) {
                    // user exists
                    System.out.println("User exists");
                    Toast.makeText(ProfileActivity.this, "User already has a registered account! Click the plus icon to follow them instead!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // user does not exist
                System.out.println("User does not exist");
                Toast.makeText(ProfileActivity.this, "Invitation email successfully sent to " + enter_friend_email.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                sendInvitationEmail();
                enter_friend_email.setText("");
            }
        });

    }

    private void sendInvitationEmail() {
        String mEmail = enter_friend_email.getText().toString() + "@usc.edu";
        String mSubject = "Talk2Friends Invitation";
        String mMessage = "Your friend, " + currentUser.getName() + ", has invited you to join Talk2Friends!\n" +
                "Talk2Friends is a social media app that allows you to set up and RSVP to meetings to practice your English, either as a native speaker or an international student!\n" +
                "Find the app, Talk2Friends, on the Google Play Store!";

        System.out.println("Sending invitation email to: " + mEmail);

        // send email
        JavaMailAPI javaMailAPI = new JavaMailAPI(ProfileActivity.this, mEmail, mSubject, mMessage);
        javaMailAPI.execute();

        // toast
        Toast.makeText(this, "Invitation sent!", Toast.LENGTH_SHORT).show();
    }

    private void loadRecommendedFriends() {
        recommendFriends(new StringArrayCallback() {
            @Override
            public void onCallback(ArrayList<String> recommendedFriends) {
                // inflate
                LinearLayout rec_friends_lin_box = recommend_friends_box.findViewById(R.id.rec_friends_lin_box);
                rec_friends_lin_box.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
                for (int i = 0; i < recommendedFriends.size(); i++) {
                    // create name button with no x
                    View tempView = inflater.inflate(R.layout.name_button, null);
                    Button tempButton = tempView.findViewById(R.id.nameButton);
                    tempButton.setText(recommendedFriends.get(i));

                    tempButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // maybe do something
                            // prefill email in the search bar
                            String justUsername = tempButton.getText().toString().substring(0, tempButton.getText().toString().indexOf("@"));
                            enter_friend_email.setText(justUsername);
                            Toast.makeText(ProfileActivity.this, "Click the follow button to follow recommended friend: " + tempButton.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    rec_friends_lin_box.addView(tempView);
                }
            }
        });



    }

    private void toggleEditPersonal () {
        loadPersonal();
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_personal_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_personal_box.setVisibility(View.GONE);
        }

    }

    private void toggleEditAddInterests() {
        loadFriends();
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            add_interests_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            add_interests_box.setVisibility(View.GONE);
        }
    }
    private void toggleEditFriends () {
        loadFriends();
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_friends_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_friends_box.setVisibility(View.GONE);
        }

    }
    private void toggleEditAddFriends () {
        loadFriends();
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            add_friends_page.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            add_friends_page.setVisibility(View.GONE);
        }
    }
    private void toggleEditMeetings () {
        // TODO:
//        loadMeetings();
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            edit_meetings_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            edit_meetings_box.setVisibility(View.GONE);
        }
    }

    private void closePopups() {
        edit_personal_box.setVisibility(View.GONE);
        edit_friends_box.setVisibility(View.GONE);
        edit_meetings_box.setVisibility(View.GONE);
        add_interests_box.setVisibility(View.GONE);
        add_friends_page.setVisibility(View.GONE);
        transparentGray.setVisibility(View.GONE);
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

        System.out.println("newType: " + newType);

        // update values in database
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "name", newName);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "age", newAge);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "affiliation", newAffiliation);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "type", newType);
    }

    private void saveInterests() {
        // save interests to database
        System.out.println("Saving interests...");

        // get the dropdowns view
        View editable_data = findViewById(R.id.add_interests_box);
        Spinner sports = editable_data.findViewById(R.id.sportsDropdown);
        Spinner music = editable_data.findViewById(R.id.musicDropdown);
        Spinner reading = editable_data.findViewById(R.id.readingDropdown);
        Spinner exercise = editable_data.findViewById(R.id.exerciseDropdown);
        Spinner movies = editable_data.findViewById(R.id.moviesDropdown);

        // get new values
        String newSports = sports.getSelectedItem().toString();
        String newMusic = music.getSelectedItem().toString();
        String newReading = reading.getSelectedItem().toString();
        String newExercise = exercise.getSelectedItem().toString();
        String newMovies = movies.getSelectedItem().toString();

        // update values in database
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "interests/sports", newSports);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "interests/music", newMusic);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "interests/reading", newReading);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "interests/exercise", newExercise);
        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "interests/movies", newMovies);
    }

    private void loadInterests(){
        System.out.println("Loading interests...");
        DatabaseHandler.getValue("users", "email", currentUser.getEmail(), new DataSnapshotCallback() {

            @Override
            public void onCallback(DataSnapshot data) {
                Spinner sports = add_interests_box.findViewById(R.id.sportsDropdown);
                Spinner music = add_interests_box.findViewById(R.id.musicDropdown);
                Spinner reading = add_interests_box.findViewById(R.id.readingDropdown);
                Spinner exercise = add_interests_box.findViewById(R.id.exerciseDropdown);
                Spinner movies = add_interests_box.findViewById(R.id.moviesDropdown);

                String[] answers = new String[]{"yes", "no"};
                ArrayAdapter<String> adapterSports = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, answers);
                adapterSports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sports.setAdapter(adapterSports);
                if (data.child("interests/sports").getValue() != null) {
                    String compareValueSports = data.child("interests/sports").getValue().toString();
                    System.out.println("compareValueSports: " + compareValueSports);
                    if (compareValueSports != null) {
                        int spinnerPosition = adapterSports.getPosition(compareValueSports);
                        sports.setSelection(spinnerPosition);
                    }
                }

                ArrayAdapter<String> adapterMusic = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, answers);
                adapterMusic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                music.setAdapter(adapterMusic);
                if (data.child("interests/music").getValue() != null) {
                    String compareValueMusic = data.child("interests/music").getValue().toString();
                    System.out.println("compareValueMusic: " + compareValueMusic);
                    if (compareValueMusic != null) {
                        int spinnerPosition = adapterMusic.getPosition(compareValueMusic);
                        music.setSelection(spinnerPosition);
                    }
                }

                ArrayAdapter<String> adapterReading = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, answers);
                adapterReading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                reading.setAdapter(adapterReading);
                if (data.child("interests/reading").getValue() != null) {
                    String compareValueReading = data.child("interests/reading").getValue().toString();
                    System.out.println("compareValueReading: " + compareValueReading);
                    if (compareValueReading != null) {
                        int spinnerPosition = adapterReading.getPosition(compareValueReading);
                        reading.setSelection(spinnerPosition);
                    }
                }

                ArrayAdapter<String> adapterExercise = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, answers);
                adapterExercise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                exercise.setAdapter(adapterExercise);
                if (data.child("interests/exercise").getValue() != null) {
                    String compareValueExercise = data.child("interests/exercise").getValue().toString();
                    System.out.println("compareValueExercise: " + compareValueExercise);
                    if (compareValueExercise != null) {
                        int spinnerPosition = adapterExercise.getPosition(compareValueExercise);
                        exercise.setSelection(spinnerPosition);
                    }
                }

                ArrayAdapter<String> adapterMovies = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, answers);
                adapterMovies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                movies.setAdapter(adapterMovies);
                if (data.child("interests/movies").getValue() != null) {
                    String compareValueMovies = data.child("interests/movies").getValue().toString();
                    System.out.println("compareValueMovies: " + compareValueMovies);
                    if (compareValueMovies != null) {
                        int spinnerPosition = adapterMovies.getPosition(compareValueMovies);
                        movies.setSelection(spinnerPosition);
                    }
                }




            }
        });
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
                        System.out.println("typeValue: " + typeValue.toString());
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


    private void loadFriends() {
        DatabaseHandler.getValue("users", "email", currentUser.getEmail(), new DataSnapshotCallback() {
            @Override
            public void onCallback(DataSnapshot data) {
                if (data == null) {
                    return;
                }
                // get arraylist of friends
                ArrayList<String> tempFriends = new ArrayList<>();
                for (DataSnapshot friend : data.child("friends").getChildren()) {
                    tempFriends.add(friend.getValue(String.class));
                }

                // display friends
                ConstraintLayout friendsBox = findViewById(R.id.friends_box);
                LinearLayout friendsLinBox = friendsBox.findViewById(R.id.friends_lin_box);

                // display edit friends
                ConstraintLayout editFriendsBox = findViewById(R.id.edit_friends_box);
                LinearLayout editFriendsLinBox = editFriendsBox.findViewById(R.id.edit_friends_lin_box);

                LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
                // inflate friends box and edit friends box
                friendsLinBox.removeAllViews();
                editFriendsLinBox.removeAllViews();
                for (int i = 0; i < tempFriends.size(); i++) {
                    final String tempParticipantEmail = tempFriends.get(i);

                    // Fetch the affiliation for the friend
                    DatabaseHandler.getValue("users", "email", tempParticipantEmail, new DataSnapshotCallback() {
                        @Override
                        public void onCallback(DataSnapshot friendData) {
                            if (friendData != null) {
                                String friendType = friendData.child("type").getValue(String.class);

                                // create name button with no x
                                View tempNoXView = inflater.inflate(R.layout.dynamic_button_no_x, null);
                                Button tempNoXButton = tempNoXView.findViewById(R.id.nameButton);
                                tempNoXButton.setText(tempParticipantEmail);
//
                                // Check if the friend should have a purple background
                                if ("international student".equals(friendType)) {
                                    // Set the text color to purple
                                    tempNoXButton.setTextColor(ContextCompat.getColor(ProfileActivity.this, android.R.color.holo_purple));
                                }

                                // create name button with x
                                View tempXView = inflater.inflate(R.layout.dynamic_button, null);
                                Button tempXButton = tempXView.findViewById(R.id.nameButton);
                                tempXButton.setText(tempParticipantEmail);

                                // add x functionality
                                ImageButton x = tempXView.findViewById(R.id.crossButton);
                                x.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String friendName = tempXButton.getText().toString();

                                        // remove friend from user
                                        currentUser.removeFriend(friendName);
                                        DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "friends", currentUser.getFriends());

                                        // Remove the button from the UI
                                        editFriendsLinBox.removeView(tempXView);
                                    }
                                });

                                // Assign unique IDs to the buttons
                                tempNoXButton.setId(View.generateViewId());
                                tempXButton.setId(View.generateViewId());

                                // add name button to linear layout
                                friendsLinBox.addView(tempNoXView);
                                editFriendsLinBox.addView(tempXView);
                            }
                        }
                    });
                }
            }
        });
    }


    private void loadMeetings()
    {
        DatabaseHandler.getValue("users", "email", currentUser.getEmail(), new DataSnapshotCallback() {
            @Override
            public void onCallback(DataSnapshot data) {
                if (data == null) {
                    return;
                }
                // get arraylist of user's meetings in database
                ArrayList<Meeting> tempMeetings = new ArrayList<>();
                for (DataSnapshot meeting : data.child("meetings").getChildren()) {
                    tempMeetings.add(meeting.getValue(Meeting.class));
                }

                // display meetings
                ConstraintLayout meetingsBox = findViewById(R.id.meetings_box);
                LinearLayout rsvpLinBox = meetingsBox.findViewById(R.id.rsvp_lin_box);

                // display edit meetings
                ConstraintLayout editMeetingsBox = findViewById(R.id.edit_meeting_box);
                LinearLayout editRsvpLinBox = editMeetingsBox.findViewById(R.id.edit_rsvp_lin_box);

                // inflate meetings box and edit meetings box
                rsvpLinBox.removeAllViews();
                editRsvpLinBox.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
                for (int i = 0; i < tempMeetings.size(); i++) {
                    String tempMeetingName = tempMeetings.get(i).getName();

                    // create name button with no x
                    View tempNoXView = inflater.inflate(R.layout.dynamic_button_no_x, null);
                    Button tempNoXButton = tempNoXView.findViewById(R.id.nameButton);
                    tempNoXButton.setText(tempMeetingName);

                    // create name button with x
                    View tempXView = inflater.inflate(R.layout.dynamic_button, null);
                    Button tempXButton = tempXView.findViewById(R.id.nameButton);
                    tempXButton.setText(tempMeetingName);

                    // add x functionality
                    ImageButton x = tempXView.findViewById(R.id.crossButton);
                    x.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String meetingName = tempXButton.getText().toString();

                            // remove meeting from user
                            for (int j = 0; j < tempMeetings.size(); j++) {
                                if (tempMeetings.get(j).getName().equals(meetingName)) {
                                    currentUser.removeMeeting(tempMeetings.get(j).getName());
                                    break;
                                }
                            }
                            DatabaseHandler.updateValue("users", "email", currentUser.getEmail(), "meetings", currentUser.getMeetings());

                            // remove user from meeting
                            DatabaseHandler.getValue("meetings", "name", meetingName, new DataSnapshotCallback() {
                                @Override
                                public void onCallback(DataSnapshot data) {
                                    if (data == null) {
                                        return;
                                    }
                                    ArrayList<String> tempMeetingParticipants = new ArrayList<>();
                                    for (DataSnapshot participant : data.child("participants").getChildren()) {
                                        if (participant.getValue(String.class).equals(currentUser.getEmail())) {
                                            continue;
                                        } else {
                                            tempMeetingParticipants.add(participant.getValue(String.class));
                                        }
                                    }
                                    DatabaseHandler.updateValue("meetings", "name", meetingName, "participants", tempMeetingParticipants);
                                }
                            });

                            // Remove the button from the UI
                            editRsvpLinBox.removeView(tempXView);
                        }
                    });

                    // Assign unique IDs to the buttons
                    tempNoXButton.setId(View.generateViewId());
                    tempXButton.setId(View.generateViewId());

                    // add name button to linear layout
                    rsvpLinBox.addView(tempNoXView);
                    editRsvpLinBox.addView(tempXView);
                }
            }
        });
    }





    private void tryAddFriend() {
        // update local copy of user
        DatabaseHandler.getUser(currentUser.getEmail(), new UserCallback() {
            @Override
            public void onCallback(User data) {
                currentUser = data;
            }
        });

        // check user not adding themselves
        String tempString = enter_friend_email.getText().toString() + "@usc.edu";
        if (tempString.equals(currentUser.getEmail())) {
            Toast.makeText(ProfileActivity.this, "Sorry, but you can't add yourself!", Toast.LENGTH_SHORT).show();
            return;
        }

        // check that user exists already
        DatabaseHandler.getUser(enter_friend_email.getText().toString() + "@usc.edu", new UserCallback() {
            @Override
            public void onCallback(User data) {
                if (data == null) {
                    // user does not exist
                    System.out.println("User does not exist");
                    Toast.makeText(ProfileActivity.this, "User does not have a registered account! Click the mail icon to invite them instead!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // user exists
                System.out.println("User exists");
                System.out.println("cur friends: " + currentUser.getFriends());
                System.out.println("email to friend: " + data.getEmail());
                if (currentUser.getFriends().contains(data.getEmail())) {
                    System.out.println("Already friends with " + data.getEmail());
                    Toast.makeText(ProfileActivity.this, "You're already following " + data.getEmail() + "!", Toast.LENGTH_SHORT).show();
                } else {
                    addFriend(data.getEmail());
                    Toast.makeText(ProfileActivity.this, "Successfully followed " + data.getEmail() + "!", Toast.LENGTH_SHORT).show();
                    enter_friend_email.setText("");
                }
            }
        });
    }

    private void addFriend(String email) {
        DatabaseHandler.pushNewFriend(currentUser.getEmail(), email);
    }


    private void recommendFriends(StringArrayCallback stringArrayCallback) {
        ArrayList<String> myInterests = currentUser.getInterests();
        ArrayList<String> recommendedFriends = new ArrayList<String>();

        // TODO: get all users from database
        ArrayList<User> allUsers = new ArrayList<User>();
        DatabaseHandler.getUsers(new UsersCallback() {
            @Override
            public void onCallback(ArrayList<User> users) {
                allUsers.addAll(users);
                // remove myself
                for (int i = 0; i < allUsers.size(); i++) {
                    if (allUsers.get(i).getEmail().equals(currentUser.getEmail())) {
                        allUsers.remove(i);
                        break;
                    }
                }

                System.out.println("All Users in Database (except me): " + allUsers);
                ArrayList<Integer> numSharedInterests = new ArrayList<Integer>();

                for (int i = 0; i < allUsers.size(); i++) {
                    Integer num = 0;
                    // formatted: e.g., ["yes", "no", "yes", "no", "no"]
                    ArrayList<String> tempInterests = myInterests;
                    ArrayList<String> otherInterests = allUsers.get(i).getInterests();
                    System.out.println("temp Interests: " + tempInterests);
                    System.out.println("other interests: " + otherInterests);
                    for (int j = 0; j < tempInterests.size(); j++) {
                        if (tempInterests.get(j).equals(otherInterests.get(j))){
                            num++;
                            System.out.println("num: " + num + " for user " + allUsers.get(i).getEmail());
                        }
                    }
                    numSharedInterests.add(num);
                }

                // TODO: find top x users with most shared interests
                int x = 3;
                if (allUsers.size() < x) {
                    x = allUsers.size();
                }
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < numSharedInterests.size(); j++) {
                        // get the max compatibility user
                        System.out.println("numSharedInterests.get(j): " + numSharedInterests.get(j));
                        System.out.println("max shared interests: " + Collections.max(numSharedInterests));
                        if (numSharedInterests.get(j) == Collections.max(numSharedInterests)) {
                            recommendedFriends.add(allUsers.get(j).getEmail());

                            // remove from searching pool
                            allUsers.remove(allUsers.get(j));
                            numSharedInterests.remove(numSharedInterests.get(j));

                            // move on to next iteration
                            break;
                        }

                    }
                }

                System.out.println("Recommended Friends: " + recommendedFriends);
                stringArrayCallback.onCallback(recommendedFriends);
            }
        });
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }




}
