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
                // check that it's a USC email!
                if (!emailInputField.getText().toString().contains("@usc.edu")) {
                    // display error message
                    System.out.println("Not a USC email!");
                    incorrectSignUpText.setText(getString(R.string.IncorrectSignUpUSCEmail));
                    return;
                }

                // check unique email in firebase
                DatabaseHandler.checkIsUnique("users", "email", emailInputField.getText().toString(), new Callback() {
                    @Override
                    public void onCallback(String value) {
                        if (value.equals("true")) {
                            // email is unique!
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

        return code;
    }

    private void getUniqueValidationCode(Callback callback) {

        // check if code is already in database\
        // from: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method/47853774
            // implement this method to call asynchronously
        // for checking if code is already in database
        database = FirebaseDatabase.getInstance("https://talk2friends-78719-default-rtdb.firebaseio.com/");
        codesRef = database.getReference("codes");
        codesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hihihi");
                String code = generateValidationCode();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("code").exists()) {
                        while (data.child("code").getValue().toString().equals(code)) {
                            System.out.println("Generated code already in database! Creating new code...");
                            code = generateValidationCode();
                        }
                        callback.onCallback(code);

                        // add code instance to database
                        codesRef.push().setValue(code);
                        return;
                    }
                }
                System.out.println("datasnapshot child code does not exist! Initializing for the first time...");
                callback.onCallback(code);

                // add code instance to database
                codesRef.push().setValue(code);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initiateSignUp() {
        System.out.println("Your email: " + emailInputField.getText().toString() + " has not been registered before! Go ahead and register!");

        if (checkSignUpErrors()) {
            // no errors
            // send email
            mEmail = emailInputField.getText().toString();
            mSubject = getString(R.string.SignUpEmailSubject);
            mMessage = getString(R.string.SignUpEmailMessage);

            // append validation code
            // generate validation code and return after confirming that it's unique in database
            getUniqueValidationCode(new Callback() {
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
            });
        } else {
            // yes errors
            return;
        }

    }

    private boolean checkSignUpErrors() {
        // Error with hash
        hash = Utils.getHash(passwordInputField.getText().toString());
        if (hash.equals("")) {
            System.out.println("Error: unable to create hash!");
            incorrectSignUpText.setText(getString(R.string.IncorrectSignUpPassword));
            return false;
        }

        // Error with empty password
        if (passwordInputField.getText().toString().equals("")) {
            System.out.println("Error: password cannot be empty!");
            incorrectSignUpText.setText(getString(R.string.IncorrectSignUpPassword));
            return false;
        }

        System.out.println("No signup errors found.");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy firebase
        usersRef = null;
        database = null;
    }
}