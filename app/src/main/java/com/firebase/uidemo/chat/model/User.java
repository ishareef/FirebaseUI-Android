package com.firebase.uidemo.chat.model;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private List<UserChat> chats;

    public User() {}

    public User(String uid, String name, List<UserChat> chats) {
        this.uid = uid;
        this.name = name;
        this.chats = chats;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public List<UserChat> getChats() {
        return chats;
    }
}
