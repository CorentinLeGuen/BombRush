package com.bombrush.room.controller.websocket;

import com.bombrush.room.dto.Subscribe;
import com.bombrush.room.service.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.HashSet;
import java.util.Set;

@Controller
public class GameWebSocketController {

    @Autowired
    private Game game;

    @MessageMapping("/info")
    @SendTo("/room")
    public Set<String> registerRoom(String roomName) {
        game.addRoom(HtmlUtils.htmlEscape(roomName));
        return game.getRoomNames();
    }

    @MessageMapping("/user")
    @SendTo("/users")
    public Set<String> sendUser(SimpMessageHeaderAccessor accessor, Subscribe sub) {
        String sessionId = accessor.getSessionId();
        if (!game.roomExists(sub.getRoomName())) {
            return new HashSet<>();
        }
        game.addNewPlayer(sub, sessionId);
        return game.getPlayersInRoom(sub.getRoomName());
    }
}
