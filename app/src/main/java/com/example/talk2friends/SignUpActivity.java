package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private TextView incorrectSignUpText;

    // firebase
    FirebaseDatabase database;
    DatabaseReference usersRef;
    DatabaseReference codesRef;

    String hash;

    // email things
    String mEmail;
    String mSubject;
    String mMessage;

    boolean donePushingCode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_up);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        emailInputField = (EditText) findViewById(R.id.emailInput);
        passwordInputField = (EditText) findViewById(R.id.passwordInput);

        incorrectSignUpText = (TextView) findViewById(R.id.incorrectSignUpText);

        // TODO: modify activity_sign_up.xml to look different from login.xml
            // TODO: add a "Welcome to Talk2Friends!" TextView
            // TODO: add a "Please enter your USC email and password to sign up" TextView

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.switchActivityLogin(SignUpActivity.this);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for testing only for new sign ups, to confirm validation code input
                if (emailInputField.getText().toString().equals("testUser@usc.edu") && passwordInputField.getText().toString().equals("newSignUp")) {
                    Utils.switchActivityValidationCode(SignUpActivity.this, emailInputField.getText().toString(), "hash", "1234");
                    return;
                }

                if (emailInputField.getText().toString().equals("testUser@usc.edu") && !passwordInputField.getText().toString().equals("oldSignUp")) {
                    // check for errors
                    if (!SignUpUtils.checkSignUpErrors(emailInputField.getText().toString(), passwordInputField.getText().toString(), incorrectSignUpText)) {
                        return;
                    }
                }

                // check unique email in firebase
                DatabaseHandler.checkIsUnique("users", "email", emailInputField.getText().toString(), new Callback() {
                    @Override
                    public void onCallback(String value) {
                        if (value.equals("true")) {
                            // email is unique!

                            // check for errors
                            if (!SignUpUtils.checkSignUpErrors(emailInputField.getText().toString(), passwordInputField.getText().toString(), incorrectSignUpText)) {
                                return;
                            }

                            initiateSignUp();

                        } else {
                            System.out.println("Your email: " + emailInputField.getText().toString() + " has been registered before! Please log in!");
                            incorrectSignUpText.setText(getString(R.string.IncorrectSignUpEmailExists));
                        }
                    }
                });


            }
        });
    }






    private void initiateSignUp() {
        System.out.println("Your email: " + emailInputField.getText().toString() + " has not been registered before! Go ahead and register!");

        hash = Utils.getHash(passwordInputField.getText().toString());
        // no errors
        // send email
        mEmail = emailInputField.getText().toString();
        mSubject = getString(R.string.SignUpEmailSubject);
        mMessage = getString(R.string.SignUpEmailMessage);

        // append validation code
        // generate validation code and return after confirming that it's unique in database
        SignUpUtils.getUniqueValidationCode(new Callback() {
            @Override
            public void onCallback(String value) {
                // append unique validation code
                mMessage += value;
                mMessage += "\n";

                // send email
                JavaMailAPI javaMailAPI = new JavaMailAPI(SignUpActivity.this, mEmail, mSubject, mMessage);
                javaMailAPI.execute();

                // switch to validation code activity
                Utils.switchActivityValidationCode(SignUpActivity.this, emailInputField.getText().toString(), hash, value);
            }
        }, new CustomFirebaseClient());


    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy firebase
        usersRef = null;
        database = null;
    }
}