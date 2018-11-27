package com.bombrush.room;

public class Room {
    private String title;

    public Room() {}

    public Room(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Room{" +
                "title='" + title + '\'' +
                '}';
    }
}
