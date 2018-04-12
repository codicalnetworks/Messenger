package com.codicalnetworks.messenger;

/**
 * Created by ADETOBA on 4/10/2018.
 */

public class ChatMessage {
    private String username;
    private String userMessage;

    public ChatMessage(String username, String userMessage) {
        this.username = username;
        this.userMessage = userMessage;
    }

    public ChatMessage() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
