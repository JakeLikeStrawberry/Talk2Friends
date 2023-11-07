package com.example.talk2friends;

import java.util.ArrayList;

public class User {
    private String email = "";
    private String password = "";
    private String name = "";
    private String age = "";
    private String affiliation = "";
    private String type = "";
    private ArrayList<String> friendsList = new ArrayList<String>();
    private ArrayList<String> interests = new ArrayList<>();


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    public ArrayList<String> getFriends() {
        return friendsList;
    }
    public ArrayList<String> getInterests() {return interests; }
}


