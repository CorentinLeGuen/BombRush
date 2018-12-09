package com.bombrush.room.Rooms;

public class User {
    private String sessionId;
    private String userName;

    public User(String sessionId, String userName) {
        this.sessionId = sessionId;
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "sessionId='" + sessionId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
