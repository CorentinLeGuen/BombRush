package com.bombrush.room;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class RoomController {

    private Set<String> rooms = new HashSet<>();
    private Map<String, Set<String>> roomToPlayers = new HashMap<>();

    @MessageMapping("/info")
    @SendTo("/room")
    public String registerRoom(String roomName) {
        String room = HtmlUtils.htmlEscape(roomName);
        if (rooms.add(room))
            roomToPlayers.put(room, new HashSet<>());
        return room;
    }

    @MessageMapping("/user")
    @SendTo("/users")
    public Set<String> sendUser(Subscribe sub) {
        if (!rooms.contains(sub.getRoomName())) {
            return null;
        }
        Set<String> players = roomToPlayers.get(sub.getRoomName());
        players.add(sub.getUser());
        return players;
    }

    @GetMapping(value = "/rooms", produces = "application/json")
    public ResponseEntity<Set<String>> getRooms() {
        return new ResponseEntity<>(rooms, OK);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Set<String>> getUsersInRoom(String roomName) {
        return new ResponseEntity<>(roomToPlayers.get(roomName), OK);
    }

    @GetMapping(value = "/room/{roomName}")
    public String goToRoom(@PathVariable String roomName) {
        if (rooms.contains(roomName)) {
            return "/roomPath/index.html";
        }
        return "../public/error/404.html";
    }
}
