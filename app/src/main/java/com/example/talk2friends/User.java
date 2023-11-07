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
    private String key = "";


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User (String tempEmail, String tempPassword, String tempName, String tempAge, String tempAffiliation, String tempType, ArrayList<String> tempFriends, ArrayList<String> tempInterests) {
        this.email = tempEmail;
        this.password = tempPassword;
        this.name = tempName;
        this.age = tempAge;
        this.affiliation = tempAffiliation;
        this.type = tempType;
        this.friendsList = tempFriends;
        this.interests = tempInterests;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}


