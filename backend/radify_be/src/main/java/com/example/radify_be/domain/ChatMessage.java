package com.example.radify_be.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String content;
    private String sender;
    private String receiver;
    private String date;
    private Status status;
}
