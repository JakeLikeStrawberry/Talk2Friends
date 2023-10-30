package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class ValidationCode extends AppCompatActivity {

    // buttons
    Button confirmButton;

    // input fields for every validation number
    EditText validationInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_code);

        // buttons
        confirmButton = (Button) findViewById(R.id.confirmButton);

        // input fields for every validation number
        validationInputField = (EditText) findViewById(R.id.validationInput);

    }



}