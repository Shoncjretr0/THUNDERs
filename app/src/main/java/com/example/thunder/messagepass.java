package com.example.thunder;

public class messagepass {
    String id;
    String to;
    String from;
    String message;
    String time;
    String date;
    String picurl;
    String videourl;
    String docurl;
    String iddentifier;
    String lat;
    String lon;
    String type;
    int seen;

    public messagepass(){

    }

    public messagepass(String id,String to, String from, String message, String time, String date, String picurl, String videourl, String docurl,  String iddentifier, String lat, String lon, String type,int seen) {
        this.id =id;
        this.to = to;
        this.from = from;
        this.message = message;
        this.time = time;
        this.date = date;
        this.picurl = picurl;
        this.videourl = videourl;
        this.docurl = docurl;
        this.iddentifier= iddentifier;
        this.lat=lat;
        this.lon=lon;
        this.type=type;
        this.seen=seen;
    }

    public int getSeen() {
        return seen;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPicurl() {
        return picurl;
    }

    public String getVideourl() {
        return videourl;
    }

    public String getDocurl() {
        return docurl;
    }

    public String getIddentifier() {
        return iddentifier;
    }
}
