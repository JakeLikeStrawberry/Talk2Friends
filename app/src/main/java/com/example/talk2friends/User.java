package com.example.talk2friends;

import android.provider.ContactsContract;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String email = "";
    private String password = "";
    private String name = "";
    private String age = "";
    private String affiliation = "";
    private String type = "";
    private ArrayList<String> friends = new ArrayList<String>();
    private ArrayList<String> interests = new ArrayList<>();
    private ArrayList<Meeting> meetings = new ArrayList<>();
    private String key = "";


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User (String tempEmail, String tempPassword, String tempName, String tempAge, String tempAffiliation, String tempType, ArrayList<String> tempFriends, ArrayList<String> tempInterests, ArrayList<Meeting> tempMeetings, String tempKey) {
        this.email = tempEmail;
        this.password = tempPassword;
        this.name = tempName;
        this.age = tempAge;
        this.affiliation = tempAffiliation;
        this.type = tempType;
        this.friends = tempFriends;
        this.interests = tempInterests;
        if (this.interests.isEmpty()) {
            this.interests = new ArrayList<>();
            this.interests.add("yes");
            this.interests.add("yes");
            this.interests.add("yes");
            this.interests.add("yes");
            this.interests.add("yes");
        }
        this.meetings = tempMeetings;
        this.key = tempKey;
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
        return friends;
    }

    public ArrayList<String> getInterests() {return interests; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addFriend(String friendEmail) {
        this.friends.add(friendEmail);
    }

    public void removeFriend(String friendEmail) {
        this.friends.remove(friendEmail);
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    public void removeMeeting(String meetingName) {
        for (int i = 0; i < this.meetings.size(); i++) {
            if (this.meetings.get(i).getName().equals(meetingName)) {
                this.meetings.remove(i);
            }
        }
    }
    public String getType() {
        return type;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


