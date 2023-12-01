package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class RequiredProfileActivity extends AppCompatActivity {

    // current User
    private String email = "";
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
        setContentView(R.layout.activity_required_profile);

        // get logged in user
        String tempEmail = getIntent().getStringExtra("email");
        if (getIntent().hasExtra("isTestMode")) {
            isTestMode = getIntent().getBooleanExtra("isTestMode", false);
        }
        System.out.println("Hi I made it here in the activity");

        // load user from ValidationCodeActivity
        email = getIntent().getStringExtra("email");



//        DatabaseHandler.getUser(tempEmail, new UserCallback() {
//            @Override
//            public void onCallback(User user) {
//                System.out.println("Before assigning user");
//                currentUser = user;
//                System.out.println("Woah I made it this far");
//                if(isTestMode){
//                    System.out.println("went into true");
//                    loadPersonal();
//                }
//                else{
//                    System.out.println("Went into else");
//                    //currentUser.setEmail("testUser@usc.edu");
//                    //System.out.println("Current user: " + currentUser.getEmail() + ", " + currentUser.getPassword());
//                    loadPersonal();
//                    loadFriends();
//                    System.out.println("loading rec friends");
//                    loadRecommendedFriends();
//                    loadMeetings();
//                }
//            }
//
//        });

        //add interests

        // edit personal
        edit_personal_box = (View) findViewById(R.id.edit_personal_box);
        edit_personal_saveButton = (ImageButton) findViewById(R.id.edit_personal_box).findViewById(R.id.saveButton);
        edit_personal_name = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.nameField);
        edit_personal_age = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.ageField);
        edit_personal_affiliation = (EditText) findViewById(R.id.edit_personal_box).findViewById(R.id.affiliationField);
        edit_personal_typeDropdown = (Spinner) findViewById(R.id.edit_personal_box).findViewById(R.id.typeDropdown);

        TextView edit_personal_email = (TextView) findViewById(R.id.edit_personal_box).findViewById(R.id.emailField);
        edit_personal_email.setText(email);

//        //add interests
//        add_interests_box = (View) findViewById(R.id.add_interests_box);
//        sports_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.sportsDropdown);
//        music_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.musicDropdown);
//        reading_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.readingDropdown);
//        exercise_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.exerciseDropdown);
//        movies_dropdown = (Spinner) findViewById(R.id.add_interests_box).findViewById(R.id.moviesDropdown);
//        add_interests_saveButton = (ImageButton) findViewById(R.id.add_interests_box).findViewById(R.id.saveButton);

        edit_personal_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePersonal();
            }
        });


        // set dropdown items
        String[] types = new String[]{"international student", "native speaker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        edit_personal_typeDropdown.setAdapter(adapter);

//        // set more dropdown items
//        String[] answers = new String[]{"yes", "no"};
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, answers);
//        sports_dropdown.setAdapter(adapter2);
//        music_dropdown.setAdapter(adapter2);
//        reading_dropdown.setAdapter(adapter2);
//        exercise_dropdown.setAdapter(adapter2);
//        movies_dropdown.setAdapter(adapter2);

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

        // check if new values are valid
        // asdf
        if (newName.equals("")) {
            Toast.makeText(RequiredProfileActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();

            TextView edit_personal_email = (TextView) edit_personal_box.findViewById(R.id.incorrectRequiredText);
            edit_personal_email.setText("Please enter all required fields");
            return;
        }
        if (newAge.equals("")) {
            Toast.makeText(RequiredProfileActivity.this, "Please enter an age", Toast.LENGTH_SHORT).show();
            TextView edit_personal_email = (TextView) edit_personal_box.findViewById(R.id.incorrectRequiredText);
            edit_personal_email.setText("Please enter all required fields");
            return;
        }
        if (newAffiliation.equals("")) {
            Toast.makeText(RequiredProfileActivity.this, "Please enter an affiliation", Toast.LENGTH_SHORT).show();
            TextView edit_personal_email = (TextView) edit_personal_box.findViewById(R.id.incorrectRequiredText);
            edit_personal_email.setText("Please enter all required fields");
            return;
        }
        if (newType.equals("")) {
            Toast.makeText(RequiredProfileActivity.this, "Please select a type", Toast.LENGTH_SHORT).show();
            TextView edit_personal_email = (TextView) edit_personal_box.findViewById(R.id.incorrectRequiredText);
            edit_personal_email.setText("Please enter all required fields");
            return;
        }

//        // get the dropdowns view
//        View editable_data_interests = findViewById(R.id.add_interests_box);
//        Spinner sports = editable_data_interests.findViewById(R.id.sportsDropdown);
//        Spinner music = editable_data_interests.findViewById(R.id.musicDropdown);
//        Spinner reading = editable_data_interests.findViewById(R.id.readingDropdown);
//        Spinner exercise = editable_data_interests.findViewById(R.id.exerciseDropdown);
//        Spinner movies = editable_data_interests.findViewById(R.id.moviesDropdown);
//
//        // get new values
//        String newSports = sports.getSelectedItem().toString();
//        String newMusic = music.getSelectedItem().toString();
//        String newReading = reading.getSelectedItem().toString();
//        String newExercise = exercise.getSelectedItem().toString();
//        String newMovies = movies.getSelectedItem().toString();
//
//        // check values
//        if (newSports.equals("")) {
//            Toast.makeText(RequiredProfileActivity.this, "Please select a value for sports", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (newMusic.equals("")) {
//            Toast.makeText(RequiredProfileActivity.this, "Please select a value for music", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (newReading.equals("")) {
//            Toast.makeText(RequiredProfileActivity.this, "Please select a value for reading", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (newExercise.equals("")) {
//            Toast.makeText(RequiredProfileActivity.this, "Please select a value for exercise", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (newMovies.equals("")) {
//            Toast.makeText(RequiredProfileActivity.this, "Please select a value for movies", Toast.LENGTH_SHORT).show();
//            return;
//        }


        // update values in database
//        saveInterests();
        CustomFirebaseClient_Update client = new CustomFirebaseClient_Update();
        DatabaseHandler.updateValue("users", "email", email, "name", newName, client);
        DatabaseHandler.updateValue("users", "email", email, "age", newAge, client);
        DatabaseHandler.updateValue("users", "email", email, "affiliation", newAffiliation, client);
        DatabaseHandler.updateValue("users", "email", email, "type", newType, client);

        // switch activity to Meetings page
        Utils.switchActivityMeetings(RequiredProfileActivity.this, email);
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
        CustomFirebaseClient_Update client = new CustomFirebaseClient_Update();
        DatabaseHandler.updateValue("users", "email", email, "interests/sports", newSports, client);
        DatabaseHandler.updateValue("users", "email", email, "interests/music", newMusic, client);
        DatabaseHandler.updateValue("users", "email", email, "interests/reading", newReading, client);
        DatabaseHandler.updateValue("users", "email", email, "interests/exercise", newExercise, client);
        DatabaseHandler.updateValue("users", "email", email, "interests/movies", newMovies, client);
    }




}
