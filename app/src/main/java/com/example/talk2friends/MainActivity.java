package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase setup (need to keep)
        FirebaseApp.initializeApp(this);

        System.out.println("You are in Main Activity! Now switching activities...");
        System.out.println("Switching activity to login...");

        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }
}