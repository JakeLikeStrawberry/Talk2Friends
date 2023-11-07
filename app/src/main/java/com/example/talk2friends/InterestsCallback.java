package com.example.talk2friends;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public interface InterestsCallback{
    void onCallback(ArrayList<Meeting> interests);
}
