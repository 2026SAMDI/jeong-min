package com.example.theamproject;

public class User {

    public String userId;
    public String password;
    public int highScore;

    public User() {
    }

    public User(
            String userId,
            String password
    ) {
        this.userId = userId;
        this.password = password;
        this.highScore = 0;
    }
}