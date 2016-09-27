package com.firebase.uidemo.chat.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ChatMessage {

    private String name;
    private String uid;
    private String text;

    public ChatMessage() {}

    public ChatMessage(String uid, String name, String text) {
        this.name = name;
        this.uid = uid;
        this.text = text;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getUid() {
        return uid;
    }
}
