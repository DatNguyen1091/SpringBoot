package com.example.demo.shared.wrappers;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Messages {
    private String MessageCode;
    private String MessageText;

    public Messages(String messageCode) {
        this.MessageCode = messageCode;
    }

    public Messages(String messageCode, String MessageText) {
        this.MessageCode = messageCode;
        this.MessageText = MessageText;
    }
}
