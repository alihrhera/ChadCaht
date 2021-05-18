package com.hrhera.cahdcaht.data.model;

import java.util.ArrayList;
import java.util.List;

public class Convrsation {
    private String id ;
    private String user1ID;
    private String user2ID;
    private User user1;
    private User user2;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    List<Message>conMessages=new ArrayList<>();
    long lastUpdateTime;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }

    public String getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }

    public List<Message> getConMessages() {
        return conMessages;
    }

    public void setConMessages(List<Message> conMessages) {
        this.conMessages = conMessages;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
