package com.example.punchcardsystem.dto;

public class UserLoginResponse {
    private int userId;
    private String role;

    public UserLoginResponse(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
