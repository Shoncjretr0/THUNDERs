package com.example.thunder;

public class post {

    String id;

    String name;
    String photos;
    String description;
    String tags;
    String profileurl;

    public post() {

    }

    public post(String id, String name, String photos, String description, String tags, String profileurl) {
        this.name = name;
        this.photos = photos;
        this.description = description;
        this.tags = tags;
        this.id = id;
        this.profileurl = profileurl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getProfileurl() {
        return profileurl;
    }
}

