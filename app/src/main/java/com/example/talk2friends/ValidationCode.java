package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ValidationCode extends AppCompatActivity {

    // buttons
    Button confirmButton;

    // input fields for every validation number
    EditText validationInputField;

    // registering account username and password
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_code);

        // buttons
        confirmButton = (Button) findViewById(R.id.confirmButton);

        // input fields for every validation number
        validationInputField = (EditText) findViewById(R.id.validationInput);

        // get previously inputted username and password
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        // check if validation code correct
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: check if validation code correct
                if (checkValidationCode()) {
                    registerAccount();

                    // switch activity to Meetings page
                    switchActivityValidationCode();
                } else {
                    // TODO: change color in UI to red

                }


            }
        });
    }

    private boolean checkValidationCode() {
        // print to console
        System.out.println("Checking validation code...");

        // TODO: check validation code correct

        return true;
    }

    private void registerAccount() {
        // print to console
        System.out.println("Registering account...");

        // TODO: register account in database

    }



}