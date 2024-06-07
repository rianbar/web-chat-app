package com.rian.webchat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
}
