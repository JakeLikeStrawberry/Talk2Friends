package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    // all meetings to display
    private LinearLayout meetingsLinearLayout;
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();

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

        loadMeetings();

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

        // display meetings
        meetingsLinearLayout = (LinearLayout) findViewById(R.id.meetingsLinearLayout);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.switchActivityProfile(MeetingsActivity.this, currentUser.getEmail());
            }
        });
        meetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMeetings();
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
        loadMeetings();

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
        Toast toast = Toast.makeText(this, "Successfully saved meeting!", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    private void displayIncorrectCreateMessage() {
        TextView incorrectCreateText = (TextView) findViewById(R.id.create_meeting_box).findViewById(R.id.incorrectCreateMeetingText);
        incorrectCreateText.setText("All fields must be filled!");
    }

    private void loadMeetings() {
        // load/refresh meetings displayed in scrollView
        DatabaseHandler.getMeetings(new MeetingsCallback() {
            @Override
            public void onCallback(ArrayList<Meeting> data) {
                meetings = data;

                // do some UI thing
                LayoutInflater inflater = LayoutInflater.from(MeetingsActivity.this);
                meetingsLinearLayout.removeAllViews();
                for (int i = 0; i < meetings.size(); i++) {
                    Meeting tempMeeting = meetings.get(i);
                    // create meeting box
                    View tempMeetingBox = inflater.inflate(R.layout.meeting_box, null);
                    Button nameText = (Button) tempMeetingBox.findViewById(R.id.titleButton);
                    TextView topicText = (TextView) tempMeetingBox.findViewById(R.id.topicField);
                    TextView timeText = (TextView) tempMeetingBox.findViewById(R.id.timeField);
                    TextView locationText = (TextView) tempMeetingBox.findViewById(R.id.locationField);
                    nameText.setText(tempMeeting.getName());
                    topicText.setText(tempMeeting.getTopic());
                    timeText.setText(tempMeeting.getTime());
                    locationText.setText(tempMeeting.getLocation());

                    // get all participants
                    ArrayList<String> participants = tempMeeting.getParticipants();
                    LinearLayout tempParticipantsLinearLayout = (LinearLayout) tempMeetingBox.findViewById(R.id.participantsLinearLayout);
                    tempParticipantsLinearLayout.removeAllViews();
                    for (int j = 0; j < participants.size(); j++) {
                        // create name button
                        View tempNameButton = inflater.inflate(R.layout.name_button, null);
                        String tempParticipantEmail = participants.get(j);
                        Button tempNameText = (Button) tempNameButton.findViewById(R.id.nameButton);
                        tempNameText.setText(tempParticipantEmail);

                        // add name button to linear layout
                        tempParticipantsLinearLayout.addView(tempNameButton);
                    }

                    // add meeting box to linear layout
                    meetingsLinearLayout.addView(tempMeetingBox);
                }


                System.out.println("Successfully (re)loaded meetings.");
            }
        });
    }

}