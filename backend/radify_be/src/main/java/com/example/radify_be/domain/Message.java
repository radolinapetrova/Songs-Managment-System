package com.example.radify_be.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message {
    private String message;
    private MessageType type;
    private String sender;
    private String time;

}
