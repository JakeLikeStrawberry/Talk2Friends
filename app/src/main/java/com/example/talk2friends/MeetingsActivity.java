package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MeetingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        System.out.println("Switched activity to meetings.");
    }
}