//package com.example.radify_be.controller;
//
//
//import com.example.radify_be.domain.ChatMessage;
//import lombok.AllArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@AllArgsConstructor
//public class ChatController {
////    private SimpMessagingTemplate template;
////
////    @MessageMapping("/message")
////    @SendTo("/chatroom/public")
////    public ChatMessage register(@Payload ChatMessage chatMessage) {
////        return chatMessage;
////    }
////
////    @MessageMapping("/private-message")
////    public ChatMessage registerPrivateMessage(@Payload ChatMessage chatMessage) {
////        template.convertAndSendToUser(chatMessage.getSender(), "/private", chatMessage);
////        return chatMessage;
////    }
//}