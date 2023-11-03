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
    String password;

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
        validationInputField = (EditText) findViewById(R.id.validationInput);

        // get previously inputted username and password
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        validationCode = getIntent().getStringExtra("validationCode");

        // check if validation code correct
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: check if validation code correct
                if (checkValidationCode()) {
                    registerAccount();

                    // TODO: switch activity to Meetings page
                    switchActivityLogin();
                } else {
                    // TODO: change color in UI to red

                }


            }
        });
    }

    private void switchActivityLogin() {
        // print to console
        System.out.println("Switching activity to Login...");

        // switch activity to SignUpActivity
        Intent intent = new Intent(this, LoginActivity.class);
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

        // TODO: register account in database
        // example of adding user instance to database
        database = FirebaseDatabase.getInstance("https://talk2friends-78719-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        User user = new User(username, password);
        myRef.push().setValue(user);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy firebase
        myRef = null;
        database = null;
    }



}