package com.project.model;

public class ApplicationLog {

    private String action;
    private String userEmail;
    private String timestamp;

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}