package com.bombrush.room;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class RoomController {
    @MessageMapping("/info")
    @SendTo("/room")
    public Room home(String roomName) {
        System.out.println("Roomname: " + roomName);
        return new Room(HtmlUtils.htmlEscape(roomName));
    }
}
