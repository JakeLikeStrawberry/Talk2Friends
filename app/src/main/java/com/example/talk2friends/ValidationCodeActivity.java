package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ValidationCodeActivity extends AppCompatActivity {

    // buttons
    Button confirmButton;

    // input fields for every validation number
    EditText validationInputField;

    // registering account username and password
    String username;
    String hash;

    String validationCode;

    // firebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_code);

        // buttons
        confirmButton = (Button) findViewById(R.id.confirmButton);

        // input fields for every validation number
        // TODO: automatically change lowercase input to uppercase! (or accept both; case-insensitive)
        validationInputField = (EditText) findViewById(R.id.validationInput);

        // get previously inputted username and password
        username = getIntent().getStringExtra("username");
        hash = getIntent().getStringExtra("hash");

        validationCode = getIntent().getStringExtra("validationCode");

        // check if validation code correct
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if validation code correct
                if (checkValidationCode()) {
                    registerAccount();

                    // switch activity to Meetings page
                    switchActivityMeetings();
                } else {
                    // TODO: change color in UI to red
                    // TODO: add back button UI to go back to login / signUp
                    System.out.println("Validation code incorrect! Please try again");

                }


            }
        });
    }

    private void switchActivityMeetings() {
        // print to console
        System.out.println("Switching activity to Meetings...");

        // switch activity to Meetings
        Intent intent = new Intent(this, MeetingsActivity.class);
        startActivity(intent);
    }

    private boolean checkValidationCode() {
        // print to console
        System.out.println("Checking validation code...");

        // check validation code correct
        if (validationInputField.getText().toString().equals(validationCode)) {
            return true;
        }
        return false;
    }

    private void registerAccount() {
        // print to console
        System.out.println("Registering account...");

        // add user instance to database
        // push to database
        User user = new User(username, hash);
        DatabaseHandler.pushToDatabase("users", user);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy firebase
        myRef = null;
        database = null;
    }



}