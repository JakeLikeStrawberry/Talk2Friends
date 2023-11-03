package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        // TODO: validate login:
        // TODO: make SHA2 hash of password
            // check email and sha2 hash of password against firebase
        database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        usersRef = database.getReference("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("email").exists()) {
                        if (data.child("email").getValue().toString().equals(emailInputField.getText().toString())) {
                            if (data.child("password").getValue().toString().equals(passwordInputField.getText().toString())) {
                                System.out.println("Login successful!");
                                // switch to meetings activity
                                switchActivityMeetings();
                                return;
                            }
                        }

                    }
                }
                System.out.println("Login failed. Either email or password is incorrect.");
                displayIncorrectLoginMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void switchActivityMeetings() {
        // print to console
        System.out.println("Switching activity to meetings...");

        // switch activity to SignUpActivity
        // TODO: change to MeetingsActivity or whatever it would be called
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    private void displayIncorrectLoginMessage() {
        TextView incorrectLoginText = (TextView) findViewById(R.id.incorrectLoginText);
        incorrectLoginText.setText(getResources().getString(R.string.IncorrectLogin));
    }



}