package com.uros.websocket.controller;

import com.uros.websocket.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        notification();
        return chatMessage;
    }

    public void notification() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("Notification");
        chatMessage.setSender("Server");
        chatMessage.setType(ChatMessage.MessageType.CHAT);

        simpMessagingTemplate.setDefaultDestination("/topic/public");
        simpMessagingTemplate.convertAndSend(chatMessage);

        log.debug(simpMessagingTemplate.getDefaultDestination());
        log.debug("ESZXDRCTVFYGBHUNJIMOK<");
    }

}
