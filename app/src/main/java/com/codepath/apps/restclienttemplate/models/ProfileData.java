package com.codepath.apps.restclienttemplate.models;

/**
 * Created by Komal on 2/27/2016.
 */
public class ProfileData {
    String name;
    String screen_name;
    String pic;
    String description;
    Integer followers;
    Integer following;
    Integer tweets;

    public ProfileData(String name, String screen_name, String pic, String description,
                       Integer followers, Integer following, Integer tweets) {
        this.name = name;
        this.screen_name = screen_name;
        this.pic = pic;
        this.description = description;
        this.followers = followers;
        this.following = following;
        this.tweets = tweets;
    }

    public ProfileData() {
        this.name = "Loading";
        this.screen_name = "Loading";
        this.pic = "Loading";
        this.description = "Loading";
        this.followers = 0;
        this.following = 0;
        this.tweets = 0;
    }

    public void copy(ProfileData data) {
        this.name = data.name;
        this.screen_name = data.screen_name;
        this.pic = data.pic;
        this.followers = data.followers;
        this.following = data.following;
        this.tweets = data.tweets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTweets() {
        return tweets;
    }

    public void setTweets(Integer tweets) {
        this.tweets = tweets;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
