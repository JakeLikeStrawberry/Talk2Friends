package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivityLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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