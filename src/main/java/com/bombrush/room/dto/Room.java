package com.bombrush.room.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Room {

    private String title;

    private Map<String, String> sessionToPlayer = new HashMap<>();

    private Set<String> players = new HashSet<>();

    public Room(String title) {
        this.title = title;
    }

    public void addPlayer(String sessionId, String playerName) {
        players.remove(sessionToPlayer.get(sessionId));
        int n = 1;
        String name = playerName;
        while (players.contains(name)) {
            n++;
            name = playerName + n;
        }
        sessionToPlayer.put(sessionId, name);
        players.add(name);

    }

    public void removePlayer(String sessionId) {
        String playerDeleted = sessionToPlayer.remove(sessionId);
        players.remove(playerDeleted);
    }

    public Set<String> getPlayers() {
        return players;
    }
}
