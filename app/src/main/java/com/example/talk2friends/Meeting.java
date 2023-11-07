package com.example.talk2friends;

import java.util.ArrayList;

public class Meeting {
    private String name = "";
    private String topic = "";
    private String time = "";
    private String location = "";
    private ArrayList<String> participants = new ArrayList<String>();
    private String key = "";

    public Meeting() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /**
     * Constructor for meeting
     * @param name name of meeting
     * @param topic topic of meeting
     * @param time time of meeting
     * @param location location of meeting
     * @param creatorEmail email of creator of meeting
     */
    public Meeting(String name, String topic, String time, String location, String creatorEmail) {
        this.name = name;
        this.topic = topic;
        this.time = time;
        this.location = location;
        this.participants.add(creatorEmail);
    }

    public Meeting(String name, String topic, String time, String location, String creatorEmail, String key) {
        this.name = name;
        this.topic = topic;
        this.time = time;
        this.location = location;
        this.participants.add(creatorEmail);
        this.key = key;
    }

    public Meeting(String name, String topic, String time, String location, ArrayList<String> participants, String key) {
        this.name = name;
        this.topic = topic;
        this.time = time;
        this.location = location;
        this.participants = participants;
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public String getKey() {
        return key;
    }

    public void addParticipant(String email) {
        participants.add(email);
    }

    public void removeParticipant(String email) {
        participants.remove(email);
    }

    public void setKey(String key) {
        this.key = key;
    }

}