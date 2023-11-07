package com.example.talk2friends;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public interface MeetingsCallback{
    void onCallback(ArrayList<Meeting> meetings);
}