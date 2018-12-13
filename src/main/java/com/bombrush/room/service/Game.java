package com.bombrush.room.service;

import com.bombrush.room.dto.Room;
import com.bombrush.room.dto.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class Game {

    @Autowired
    private SimpMessagingTemplate template;

    private Map<String, Room> rooms = new HashMap<>();

    private Map<String, String> sessionIdToRoom = new HashMap<>();

    private Map<String, Boolean> roomUsed = new HashMap<>();

    public Set<String> getPlayersInRoom(String roomName) {
        return rooms.get(roomName).getPlayers();
    }

    public Set<String> getRoomNames() {
        return rooms.keySet();
    }

    public void addRoom(String roomName) {
        if (!roomExists(roomName)) {
            rooms.put(roomName, new Room(roomName));
            roomUsed.put(roomName, false);
        }
    }

    public boolean roomExists(String roomName) {
        return rooms.containsKey(roomName);
    }

    public void addNewPlayer(Subscribe sub, String sessionId) {
        roomUsed.replace(sub.getRoomName(), true);
        Room room = rooms.get(sub.getRoomName());
        sessionIdToRoom.put(sessionId, sub.getRoomName());
        room.addPlayer(sessionId, sub.getUser());
    }

    public void disconnectUser(String sessionId) {
        String roomName = sessionIdToRoom.get(sessionId);
        if (roomName != null) {
            rooms.get(roomName).removePlayer(sessionId);
            template.convertAndSend("/users", rooms.get(roomName).getPlayers());
            if (roomUsed.get(roomName) && rooms.get(roomName).getPlayers().isEmpty()) {
                rooms.remove(roomName);
                sessionIdToRoom.remove(roomName);
                roomUsed.remove(roomName);
            }
        }
    }
}
