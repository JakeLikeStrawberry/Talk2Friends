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

                // append validation code
                String validationCode = generateValidationCode();
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void switchActivityValidationCode() {
        // print to console
        System.out.println("Switching activity to ValidationCode...");

        // switch activity to ValidationCodeActivity
        Intent intent = new Intent(this, ValidationCode.class);
        intent.putExtra("username",emailInputField.getText().toString());
        intent.putExtra("password",passwordInputField.getText().toString());
        startActivity(intent);
    }

    private String generateValidationCode() {
        // generate 4-character alphanumeric code to send
            // 48 ~ 57: ASCII digits
            // 65 ~ 90: ASCII uppercase letters
        String code = "";
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 36);
            if (random < 10) {
                // random is a digit
                code += (char) (random + 48);
            } else {
                // random is a letter
                code += (char) (random + 55);
            }
        }

        // TODO: check if code is already in database

        // TODO: store code in database


        return code;
    }
}