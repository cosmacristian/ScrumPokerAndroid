package com.example.scrumpoker.Models;

public class Users {
    public String UID;
    public String Role;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String uid, String role) {
        this.UID = uid;
        this.Role = role;
    }
}
