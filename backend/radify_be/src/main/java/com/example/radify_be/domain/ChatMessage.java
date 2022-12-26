package com.example.radify_be.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String Sender;

    public enum MessageType{
        CHAT, LEAVE, JOIN
    }
}
