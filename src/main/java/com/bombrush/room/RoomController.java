package com.bombrush.room;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class RoomController {

    private Map<String, Room> rooms = new HashMap<>();

    @MessageMapping("/info")
    @SendTo("/room")
    public Set<String> registerRoom(String roomName) {
        String room = HtmlUtils.htmlEscape(roomName);
        if (!rooms.containsKey(room))
            rooms.put(room, new Room(room));
        return rooms.keySet();
    }

    @MessageMapping("/user")
    @SendTo("/users")
    public Set<String> sendUser(SimpMessageHeaderAccessor accessor, Subscribe sub) {
        String sessionId = accessor.getSessionId();
        System.out.println("Session ID : " + sessionId + " number of players : " + rooms.get(sub.getRoomName()).getPlayers().size());
        if (!rooms.containsKey(sub.getRoomName())) {
            return null;
        }
        Room room = rooms.get(sub.getRoomName());
        room.addPlayer(sessionId, sub.getUser());
        return room.getPlayers();
    }

    @GetMapping(value = "/rooms", produces = "application/json")
    public ResponseEntity<Set<String>> getRooms() {
        return new ResponseEntity<>(rooms.keySet(), OK);
    }

    @GetMapping(value = "/players", produces = "application/json")
    public ResponseEntity<Set<String>> getPlayersInRoom(String roomName) {
        return new ResponseEntity<>(rooms.get(roomName).getPlayers(), OK);
    }

    @GetMapping(value = "/room/{roomName}")
    public String goToRoom(@PathVariable String roomName) {
        if (rooms.containsKey(roomName)) {
            return "/roomPath/index.html";
        }
        return "../public/error/404.html";
    }
}
