package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;


public class SignUpActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;

    private EditText emailInputField;
    private EditText passwordInputField;

    // firebase
    FirebaseDatabase database;
    DatabaseReference usersRef;

    String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_up);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        emailInputField = (EditText) findViewById(R.id.emailInput);
        passwordInputField = (EditText) findViewById(R.id.passwordInput);

        // TODO: modify activity_sign_up.xml to look different from login.xml
            // TODO: add a "Welcome to Talk2Friends!" TextView
            // TODO: add a "Please enter your USC email and password to sign up" TextView

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivityLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check that it's a USC email!
                if (!emailInputField.getText().toString().contains("@usc.edu")) {
                    // TODO: display error message
                    System.out.println("Not a USC email!");
                    return;
                }

                // check that email is not already in database
                // for checking if email is already in database
                database = FirebaseDatabase.getInstance("https://talk2friends-78719-default-rtdb.firebaseio.com/");
                usersRef = database.getReference("users");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if (data.child("email").exists()) {
                                if (data.child("email").getValue().toString().equals(emailInputField.getText().toString())) {
                                    System.out.println("Email already exists!");
                                    // TODO: display error message
                                    System.out.println("Display UI error message that email already exists!");
                                    return;
                                }

                            }
                        }
                        System.out.println("Your email: " + emailInputField.getText().toString() + " does not exist! Go ahead and register!");

                        hash = Utils.getHash(passwordInputField.getText().toString());
                        if (hash.equals("")) {
                            System.out.println("Error: unable to create hash!");
                            // TODO: UI error message
                            return;
                        }
                        if (passwordInputField.getText().toString().equals("")) {
                            System.out.println("Error: password cannot be empty!");
                            // TODO: UI error message
                            return;
                        }

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
                        switchActivityValidationCode(validationCode);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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

    private void switchActivityValidationCode(String validationCode) {
        // print to console
        System.out.println("Switching activity to ValidationCode...");

        // switch activity to ValidationCodeActivity
        Intent intent = new Intent(this, ValidationCodeActivity.class);
        intent.putExtra("username",emailInputField.getText().toString());
        intent.putExtra("hash",hash);
        intent.putExtra("validationCode",validationCode);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy firebase
        usersRef = null;
        database = null;
    }
}