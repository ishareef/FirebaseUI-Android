package com.firebase.uidemo.chat.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserChat {

    private String chatId;
    private String title;
    private String lastMessage;

    public UserChat(String title, String message) {
        this.title = title;
        this.lastMessage = message;
    }

    public String getTitle() {
        return title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getKey() { return chatId; }
}
