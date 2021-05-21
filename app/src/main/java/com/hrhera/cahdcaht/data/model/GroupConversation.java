package com.hrhera.cahdcaht.data.model;

import java.util.ArrayList;
import java.util.List;

public class GroupConversation {
    String id;
    String name;
    String icon;
    private ChatUser admin;
    private List<String> chatUsersId;
    List<Message> conMessages = new ArrayList<>();
    long lastUpdateTime;
    private long createTime;
    private List<ChatUser> chatUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChatUser getAdmin() {
        return admin;
    }

    public void setAdmin(ChatUser admin) {
        this.admin = admin;
    }

    public List<String> getChatUsersId() {
        return chatUsersId;
    }

    public void setChatUsersId(List<String> chatUsersId) {
        this.chatUsersId = chatUsersId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }
}
