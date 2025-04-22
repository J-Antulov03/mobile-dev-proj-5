package edu.uga.cs.theridesharingapp;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

public class Ride {

    //date = date and time (upgraded now)

    //author = user who posted

    //rideType = true (request)
    //rideType = false (offer)

    private String key;
    private Date date;
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

    public Ride(Date date, String startLoc, String destLoc, Boolean rideType) {
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
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
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

    public String getFormattedDate(Context context) {
        if (date == null) return "";
        return DateFormat.getDateFormat(context).format(date) + " " +
                DateFormat.getTimeFormat(context).format(date);
    }

    @Override
    public String toString() {
        // Note: This won't be able to format the date properly without context
        return (date != null ? date.toString() : "null") + " " + startLoc + " " +
                destLoc + " " + accepted + " " + rideType;
    }
}
