package com.hrhera.cahdcaht.data.model;

public class User {
    private String id;
    private String name;
    private String bio;
    private String phone;
    private String password;
    private boolean Active;
    private long joinData;
    private long lastLoginTime;
    private String photoLink;
    private int coverPhotoID;
    int userIconId;

    public int getUserIconId() {
        return userIconId;
    }

    public void setUserIconId(int userIconId) {
        this.userIconId = userIconId;
    }

    public int getCoverPhotoID() {
        return coverPhotoID;
    }

    public void setCoverPhotoID(int coverPhotoID) {
        this.coverPhotoID = coverPhotoID;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public long getJoinData() {
        return joinData;
    }

    public void setJoinData(long joinData) {
        this.joinData = joinData;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
