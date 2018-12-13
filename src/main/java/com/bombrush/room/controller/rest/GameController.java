package com.bombrush.room.controller.rest;

import com.bombrush.room.service.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class GameController {

    @Autowired
    private Game game;

    @GetMapping(value = "/rooms", produces = "application/json")
    public ResponseEntity<Set<String>> getRooms() {
        return new ResponseEntity<>(game.getRoomNames(), OK);
    }

    @GetMapping(value = "/players", produces = "application/json")
    public ResponseEntity<Set<String>> getPlayersInRoom(String roomName) {
        return new ResponseEntity<>(game.getPlayersInRoom(roomName), OK);
    }

    @GetMapping(value = "/room/{roomName}")
    public String goToRoom(@PathVariable String roomName) {
        if (game.roomExists(roomName)) {
            return "/roomPath/index.html";
        }
        return "../public/error/404.html";
    }
}
