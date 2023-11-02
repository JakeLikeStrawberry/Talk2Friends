package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;

    private EditText emailInputField;
    private EditText passwordInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        emailInputField = (EditText) findViewById(R.id.emailInput);
        passwordInputField = (EditText) findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivityLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = emailInputField.getText().toString();
                String mSubject = getString(R.string.SignUpEmailSubject);
                String mMessage = getString(R.string.SignUpEmailMessage);

                // TODO: create validation code
                String validationCode = "1234";
                mMessage += validationCode;
                mMessage += "\n";

                // send email
                JavaMailAPI javaMailAPI = new JavaMailAPI(SignUpActivity.this, mEmail, mSubject, mMessage);
                javaMailAPI.execute();

                // switch activity to ValidationCodeActivity
                switchActivityValidationCode();
            }
        });
    }

    private void switchActivityLogin() {
        // print to console
        System.out.println("Switching activity to Login...");

        // switch activity to SignUpActivity
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void switchActivityValidationCode() {
        // print to console
        System.out.println("Switching activity to ValidationCode...");

        // switch activity to ValidationCodeActivity
        Intent myIntent = new Intent(this, ValidationCode.class);
        startActivity(myIntent);
    }
}