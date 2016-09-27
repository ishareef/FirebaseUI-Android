package com.firebase.uidemo.chat.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat {

    private String title;
    private String lastMessage;

    public Chat() {}

    public Chat(String title, String lastMessage) {
        this.title = title;
        this.lastMessage = lastMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
