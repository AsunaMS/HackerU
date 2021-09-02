package com.mikiandor.welearn.Objects;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String userName;
    private String userId;
    private long messageTime;
    private boolean read;

    public ChatMessage(String messageText, String userName, String userId) {
        this.messageText = messageText;
        this.userName = userName;
        this.userId = userId;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}