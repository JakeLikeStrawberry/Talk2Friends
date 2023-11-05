package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton profileButton;
    private ImageButton meetingsButton;
    private ImageButton editButton_personal;
    private ImageButton editButton_friends;
    private ImageButton editButton_meetings;
    private Spinner typeDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.profileButton);
        meetingsButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.meetingsButton);
        editButton_personal = (ImageButton) findViewById(R.id.personal_box).findViewById(R.id.editButton);
        editButton_friends = (ImageButton) findViewById(R.id.friends_box).findViewById(R.id.editButton);
        editButton_meetings = (ImageButton) findViewById(R.id.meetings_box).findViewById(R.id.editButton);
        typeDropdown = (Spinner) findViewById(R.id.edit_personal_box).findViewById(R.id.typeDropdown);

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
                switchActivityEditPersonal();
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

    private void switchActivityEditPersonal() {
        // print to console
        System.out.println("Editing personal box...");

        // TODO: change UI to editing...

    }






}
