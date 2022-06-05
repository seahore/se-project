package com.example.news_port1.model;

public class Follow {
    private String userId;

    private String userIdFollowed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdFollowed() {
        return userIdFollowed;
    }

    public void setUserIdFollowed(String userIdFollowed) {
        this.userIdFollowed = userIdFollowed;
    }
}
