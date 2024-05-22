package com.rian.webchat.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseErrorTemplate {
    private int code;
    private String status;
    private String message;
}
