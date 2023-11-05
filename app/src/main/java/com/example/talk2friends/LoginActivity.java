package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button signUpButton;
    private Button loginButton;

    private EditText emailInputField;
    private EditText passwordInputField;

    FirebaseDatabase database;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // buttons
        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        // input fields
        emailInputField = (EditText) findViewById(R.id.emailInput);
        passwordInputField = (EditText) findViewById(R.id.passwordInput);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivitySignUp();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogin();
            }
        });
    }

    private void switchActivitySignUp() {
        // print to console
        System.out.println("Switching activity to signUp...");

        // switch activity to SignUpActivity
        Intent myIntent = new Intent(this, SignUpActivity.class);
        startActivity(myIntent);
    }

    private void tryLogin() {
        // print to console
        System.out.println("Validating login...");

        // get input values
        String emailInput = emailInputField.getText().toString();
        String passwordInput = passwordInputField.getText().toString();

        // sanity check
        System.out.println("Email: " + emailInput);
        System.out.println("Password: " + passwordInput);

        // validate login:
            // check email and sha2 hash of password against firebase
        validateLogin();
//        database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
//        usersRef = database.getReference("users");
//        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    if (data.child("email").exists()) {
//                        if (data.child("email").getValue().toString().equals(emailInputField.getText().toString())) {
//                            String tempHash = Utils.getHash(passwordInputField.getText().toString());
//                            if (data.child("password").getValue().toString().equals(tempHash)) {
//                                System.out.println("Login successful!");
//                                // switch to meetings activity
//                                switchActivityMeetings();
//                                return;
//                            }
//                        }
//
//                    }
//                }
//                System.out.println("Login failed. Either email or password is incorrect.");
//                displayIncorrectLoginMessage();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    private void switchActivityMeetings() {
        // print to console
        System.out.println("Switching activity to meetings...");

        // switch activity to MeetingsActivity
        Intent myIntent = new Intent(this, MeetingsActivity.class);
        startActivity(myIntent);
    }

    private void switchActivityProfile() {
        // print to console
        System.out.println("Switching activity to Profile...");

        // switch activity to ProfileAtivity
        Intent myIntent = new Intent(this, ProfileActivity.class);
        startActivity(myIntent);
    }

    private void displayIncorrectLoginMessage() {
        TextView incorrectLoginText = (TextView) findViewById(R.id.incorrectLoginText);
        incorrectLoginText.setText(getResources().getString(R.string.IncorrectLogin));
    }

    private void validateLogin() {
        // get email
        DatabaseHandler.getValue("users", "email", emailInputField.getText().toString(), new DataSnapshotCallback() {
            @Override
            public void onCallback(DataSnapshot data) {
                if (data != null) {
                    // if not null...found email
                    String tempHash = Utils.getHash(passwordInputField.getText().toString());
                    if (data.child("password").getValue().toString().equals(tempHash)) {
                        System.out.println("Login successful!");
                        // switch to meetings activity
//                        switchActivityMeetings();
                        switchActivityProfile();
                        return;
                    }

                }
                // if null or password wrong
                System.out.println("Login failed. Either email or password is incorrect.");
                displayIncorrectLoginMessage();
            }
        });

    }



}