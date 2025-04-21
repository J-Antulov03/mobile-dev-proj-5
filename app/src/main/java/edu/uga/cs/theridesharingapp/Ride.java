package edu.uga.cs.theridesharingapp;

public class Ride {

    //date = date and time

    //author = user who posted

    //rideType = true (request)
    //rideType = false (offer)

    private String key;
    private String date;
    private String startLoc;
    private String destLoc;
    private String author;
    private Boolean accepted;
    private Boolean rideType;

    public Ride()
    {
        this.key = null;
        this.date = null;
        this.startLoc = null;
        this.destLoc = null;
        this.author = null;
        this.accepted = null;
        this.rideType = null;

    }

    public Ride(String date, String startLoc, String destLoc, Boolean rideType) {
        this.key = null;
        this.date = date;
        this.startLoc = startLoc;
        this.destLoc = destLoc;
        this.author = null;
        this.accepted = null;
        this.rideType = rideType;
    }


    public String getKey(){ return key; }
    public void setKey(String key) { this.key = key; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStartLoc() { return startLoc; }
    public void setStartLoc(String startLoc) { this.startLoc = startLoc; }
    public String getDestLoc() { return destLoc; }
    public void setDestLoc(String destLoc) { this.destLoc = destLoc; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Boolean isAccepted() { return accepted; }
    public void setAccepted(Boolean accepted) {this.accepted = accepted; }
    public Boolean getRideType() { return rideType; }
    public void setRideType(Boolean rideType) {this.rideType = rideType; }
    public String toString() { return date + " " + startLoc + " " + destLoc + " " + accepted + " " + rideType; }
}
