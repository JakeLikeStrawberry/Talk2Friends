package com.example.talk2friends;

import com.google.firebase.database.DataSnapshot;

public interface Callback {
    public String value = "";
    void onCallback(String value);
}

