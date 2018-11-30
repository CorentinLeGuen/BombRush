package com.bombrush.room;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class RoomController {

    private List<Room> rooms = new ArrayList<>();
    private List<String> users = new ArrayList<>();

    @MessageMapping("/info")
    @SendTo("/room")
    public Room home(String roomName) {
        for (Room r : rooms) {
            if (r.getTitle().equals(roomName))
                return null;
        }

        Room room = new Room(HtmlUtils.htmlEscape(roomName));
        rooms.add(room);
        System.out.println("Roomname: " + roomName);
        return room;
    }

    @MessageMapping("/user")
    @SendTo("/users")
    public List<String> sendUser(String user) {
        if (!users.contains(HtmlUtils.htmlEscape(user))) {
            users.add(HtmlUtils.htmlEscape(user));
        }
        return users;
    }

    @GetMapping(value = "/rooms", produces = "application/json")
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(rooms, OK);
    }

    @GetMapping(value = "/room/{roomName}")
    public String goToRoom(@PathVariable String roomName) {
        for (Room r : rooms) {
            if (r.getTitle().equals(roomName)) {
                return "/roomPath/index.html";
            }
        }
        return "../public/error/404.html";
    }
}
