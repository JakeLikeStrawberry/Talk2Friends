package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    // topbar
    private ImageButton profileButton;
    private ImageButton meetingsButton;

    // edit buttons
    private ImageButton editButton_personal;
    private ImageButton editButton_friends;
    private ImageButton editButton_meetings;

    // edit personal
    private TextView transparentGray;
    private View edit_personal_box;
    private Spinner typeDropdown;
    private ImageButton edit_personal_saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // top bar
        profileButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.profileButton);
        meetingsButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.meetingsButton);

        // edit buttons
        editButton_personal = (ImageButton) findViewById(R.id.personal_box).findViewById(R.id.editButton);
        editButton_friends = (ImageButton) findViewById(R.id.friends_box).findViewById(R.id.editButton);
        editButton_meetings = (ImageButton) findViewById(R.id.meetings_box).findViewById(R.id.editButton);

        // edit personal
        typeDropdown = (Spinner) findViewById(R.id.edit_personal_box).findViewById(R.id.typeDropdown);
        transparentGray = (TextView) findViewById(R.id.transparentGray);
        edit_personal_box = (View) findViewById(R.id.edit_personal_box);
        edit_personal_saveButton = (ImageButton) findViewById(R.id.edit_personal_box).findViewById(R.id.saveButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivityProfile();
            }
        });
        meetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivityMeetings();
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

        // set dropdown items
        String[] types = new String[]{"international student", "native speaker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typeDropdown.setAdapter(adapter);




    }


    private void switchActivityProfile() {
        // print to console
        System.out.println("Switching activity to Profile...");

        // switch activity to ProfileActivity
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void switchActivityMeetings() {
        // print to console
        System.out.println("Switching activity to Meetings...");

        // switch activity to MeetingsActivity
        Intent intent = new Intent(this, MeetingsActivity.class);
        startActivity(intent);
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

    private void savePersonal() {
        // TODO: save personal info to database
        System.out.println("Saving personal info...");
    }






}
