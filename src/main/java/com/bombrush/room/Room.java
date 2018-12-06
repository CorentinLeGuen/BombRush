package com.bombrush.room;

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

    public void addPlayer(String sessionId, String player) {
        if (!players.contains(player)) {
            players.remove(sessionToPlayer.get(sessionId));
            sessionToPlayer.put(sessionId, player);
            players.add(player);
        }
    }

    public Set<String> getPlayers() {
        return players;
    }
}
