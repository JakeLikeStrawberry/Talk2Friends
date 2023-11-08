package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
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

    // create meeting fields
    private EditText nameField;
    private EditText topicField;
    private EditText timeField;
    private EditText locationField;

    // all meetings to display
    private LinearLayout meetingsLinearLayout;
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();

    // all current popups
    private ArrayList<View> popups = new ArrayList<View>();

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
                showCreate();
            }
        });
        transparentGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCreateMeeting();
                deleteAllPopBoxes();
            }
        });



    }

    // toggle visibility of create meeting box
    private void toggleCreateMeeting () {
        loadMeetings();
        System.out.println("Toggling create meeting box visibility.");
        if (transparentGray.getVisibility() == View.GONE) {
            transparentGray.setVisibility(View.VISIBLE);
        } else if (transparentGray.getVisibility() == View.VISIBLE) {
            transparentGray.setVisibility(View.GONE);
        }
    }

    // save meeting to database
    private boolean saveMeeting() {
        if (nameField.getText().toString().equals("") || topicField.getText().toString().equals("") || timeField.getText().toString().equals("") || locationField.getText().toString().equals("")) {
            return false;
        }
        Meeting newMeeting = new Meeting(nameField.getText().toString(), topicField.getText().toString(), timeField.getText().toString(), locationField.getText().toString(), currentUser.getEmail());
        // add meeting to user
        DatabaseHandler.pushNewValue("users/" + currentUser.getKey() + "/meetings", newMeeting);
        // add meeting to meetings
        DatabaseHandler.pushNewValue("meetings", newMeeting);
        Toast toast = Toast.makeText(this, "Successfully saved and RSVPed to meeting!", Toast.LENGTH_SHORT);
        toast.show();

        return true;
    }

    private void displayIncorrectCreateMessage(View view) {
        TextView incorrectCreateText = (TextView) view.findViewById(R.id.incorrectCreateMeetingText);
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
                    View tempMeetingBox = makeMeetingBox(inflater, tempMeeting, i);

                    popups.add(tempMeetingBox);

                    // add meeting box to linear layout
                    meetingsLinearLayout.addView(tempMeetingBox);
                }


                System.out.println("Successfully (re)loaded meetings.");
            }
        });
    }

    private View makeCreateBox(LayoutInflater inflater) {
        // create create meeting box
        View tempMeetingBox = inflater.inflate(R.layout.create_meeting_box, null);
        nameField = (EditText) tempMeetingBox.findViewById(R.id.nameField);
        topicField = (EditText) tempMeetingBox.findViewById(R.id.topicField);
        timeField = (EditText) tempMeetingBox.findViewById(R.id.timeField);
        locationField = (EditText) tempMeetingBox.findViewById(R.id.locationField);

        // set on click listener for create button
        ImageButton createButton = (ImageButton) tempMeetingBox.findViewById(R.id.saveButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveMeeting()) {
                    // delete rsvp box if rsvp button clicked
                    deleteAllPopBoxes();

                    // hide transparent gray
                    transparentGray.setVisibility(View.GONE);

                    // reload meetings
                    loadMeetings();
                } else {
                    // display error message in UI saying that all fields must be filled
                    displayIncorrectCreateMessage(tempMeetingBox);
                    System.out.println("All fields must be filled!");
                }

            }
        });


        return tempMeetingBox;

    }

    private void showCreate() {
        // modify create popup box to have same information
        LayoutInflater inflater = LayoutInflater.from(MeetingsActivity.this);
        View createBox = makeCreateBox(inflater);

        // add layout params
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        createBox.setLayoutParams(params);
//        RSVPbox.gravity = Gravity.CENTER;

//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:layout_margin="20dp"
//        android:gravity="center"

        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.parent);

        transparentGray.setVisibility(View.VISIBLE);
        parent.addView(createBox);
        popups.add(createBox);
    }

    private View makeMeetingBox(LayoutInflater inflater, Meeting tempMeeting, int i) {
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

        // set on click listener for name button
        nameText.setOnClickListener(view -> showRSVP(i));

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

        return tempMeetingBox;

    }

    private View makeRSVPBox(LayoutInflater inflater, Meeting tempMeeting, int i) {
        // create meeting box
        View tempMeetingBox = inflater.inflate(R.layout.rsvp_box, null);
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

        // set on click listener for rsvp button
        ImageButton rsvpButton = (ImageButton) tempMeetingBox.findViewById(R.id.rsvpButton);
        rsvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add user to meeting
                rsvpToMeeting(i);

                // delete rsvp box if rsvp button clicked
                deleteAllPopBoxes();

                // hide transparent gray
                transparentGray.setVisibility(View.GONE);

                // refresh meetings
                loadMeetings();
            }
        });


        return tempMeetingBox;

    }


    private void showRSVP(int i) {
        // modify RSVP popup box to have same information
        LayoutInflater inflater = LayoutInflater.from(MeetingsActivity.this);
        View RSVPbox = makeRSVPBox(inflater, meetings.get(i), i);

        popups.add(RSVPbox);

        // add layout params
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        RSVPbox.setLayoutParams(params);
//        RSVPbox.gravity = Gravity.CENTER;

//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:layout_margin="20dp"
//        android:gravity="center"

        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.parent);

        transparentGray.setVisibility(View.VISIBLE);
        parent.addView(RSVPbox);

        // delete rsvp box if transparent gray clicked
        transparentGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked transparent gray, removing rsvp box");
                parent.removeView(RSVPbox);
                transparentGray.setVisibility(View.GONE);
            }
        });
    }

    private void rsvpToMeeting(int i) {
        // add user to meeting
        DatabaseHandler.pushNewValue("meetings/" + meetings.get(i).getKey() + "/participants", currentUser.getEmail());
        // add meeting to user
        DatabaseHandler.pushNewValue("users/" + currentUser.getKey() + "/meetings", meetings.get(i));
        // toast
        Toast toast = Toast.makeText(this, "Successfully RSVP'd!", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void deleteAllPopBoxes() {
        System.out.println("Deleting all popup boxes");
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.parent);
        for(int i = 0; i < popups.size(); i++) {
            parent.removeView(popups.get(i));
        }

    }

}