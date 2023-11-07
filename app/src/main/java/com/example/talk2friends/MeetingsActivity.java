package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MeetingsActivity extends AppCompatActivity {

    User currentUser;

    // topbar
    private ImageButton profileButton;
    private ImageButton meetingsButton;

    private ImageButton createMeetingButton;

    // create meeting
    private TextView transparentGray;
    private View create_meeting_box;
    private ImageButton saveButton;

    // create meeting fields
    private EditText nameField;
    private EditText topicField;
    private EditText timeField;
    private EditText locationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        System.out.println("Switched activity to meetings.");

        // get logged in user
        String tempEmail = getIntent().getStringExtra("email");
        DatabaseHandler.getUser(tempEmail, new UserCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
                System.out.println("Current user: " + currentUser.getEmail() + ", " + currentUser.getPassword());
            }
        });

        // top bar
        profileButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.profileButton);
        meetingsButton = (ImageButton) findViewById(R.id.includeSharedBackground).findViewById(R.id.meetingsButton);

        createMeetingButton = (ImageButton) findViewById(R.id.createMeetingButton);

        // create meeting
        transparentGray = (TextView) findViewById(R.id.transparentGray);
        create_meeting_box = (View) findViewById(R.id.create_meeting_box);
        saveButton = (ImageButton) findViewById(R.id.create_meeting_box).findViewById(R.id.saveButton);

        // create meeting fields
        nameField = (EditText) findViewById(R.id.create_meeting_box).findViewById(R.id.nameField);
        topicField = (EditText) findViewById(R.id.create_meeting_box).findViewById(R.id.topicField);
        timeField = (EditText) findViewById(R.id.create_meeting_box).findViewById(R.id.timeField);
        locationField = (EditText) findViewById(R.id.create_meeting_box).findViewById(R.id.locationField);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.switchActivityProfile(MeetingsActivity.this, currentUser.getEmail());
            }
        });

        createMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEditPersonal();
            }
        });
        transparentGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEditPersonal();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveMeeting()) {
                    toggleEditPersonal();
                } else {
                    // TODO: display error message in UI saying that all fields must be filled
                    displayIncorrectCreateMessage();
                    System.out.println("All fields must be filled!");
                }

            }
        });


    }

    // toggle visibility of create meeting box
    private void toggleEditPersonal () {
        // TODO: refresh meetings displayed in scrollView

        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
            create_meeting_box.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
            create_meeting_box.setVisibility(View.GONE);
        }

    }

    // save meeting to database
    private boolean saveMeeting() {
        if (nameField.getText().toString().equals("") || topicField.getText().toString().equals("") || timeField.getText().toString().equals("") || locationField.getText().toString().equals("")) {
            return false;
        }
        DatabaseHandler.pushNewValue("meetings", new Meeting(nameField.getText().toString(), topicField.getText().toString(), timeField.getText().toString(), locationField.getText().toString(), currentUser.getEmail()));
        return true;
    }

    private void displayIncorrectCreateMessage() {
        TextView incorrectCreateText = (TextView) findViewById(R.id.create_meeting_box).findViewById(R.id.incorrectCreateMeetingText);
        incorrectCreateText.setText("All fields must be filled!");
    }

}