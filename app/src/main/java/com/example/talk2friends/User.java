package com.example.talk2friends;

public class User {
    private String email = "";
    private String password = "";
    private String name = "";
    private String age = "";
    private String affiliation = "";
    private String type = "";

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
}

