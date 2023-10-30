package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button signUpButton;
    private Button loginButton;

    private EditText emailInputField;
    private EditText passwordInputField;

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

        // TODO: change to false
        boolean isValid = true;

        // TODO: validate login:
            // check email and sha2 hash of password against database

        if (isValid) {
            // print to console
            System.out.println("Login successful!");

            // switch to meetings activity
            switchActivityMeetings();
        } else {
            // print to console
            System.out.println("Login failed!");
        }


    }

    private void switchActivityMeetings() {
        // print to console
        System.out.println("Switching activity to meetings...");

        // switch activity to SignUpActivity
        // TODO: change to MeetingsActivity or whatever it would be called
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }



}