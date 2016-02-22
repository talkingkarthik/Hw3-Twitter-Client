package com.codepath.apps.restclienttemplate.models;

/**
 * Created by Komal on 2/20/2016.
 */
public class TweetItem {
    String userName;
    String name;
    String body;
    String imageUrl;
    String timeStamp;
    String profileUrl;
    long id;

    public TweetItem(long id, String userName, String profileUrl, String timeStamp, String imageUrl, String body, String name) {
        this.userName = userName;
        this.profileUrl = profileUrl;
        this.timeStamp = timeStamp;
        this.imageUrl = imageUrl;
        this.body = body;
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
