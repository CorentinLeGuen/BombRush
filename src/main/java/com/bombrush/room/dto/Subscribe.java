package com.bombrush.room.dto;

public class Subscribe {

    private String user;

    private String roomName;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "user='" + user + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
