package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ChatController {
  @MessageMapping("/chat.register")
  @SendTo("/topic/public")
  public ChatMessage register(
      @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    chatMessage.setTime(LocalDateTime.now());
    return chatMessage;
  }

  @MessageMapping("/chat.send")
  @SendTo("/topic/public")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    chatMessage.setTime(LocalDateTime.now());
    return chatMessage;
  }
}
