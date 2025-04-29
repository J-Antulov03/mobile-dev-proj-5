package edu.uga.cs.theridesharingapp;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

/**
 * Ride object
 */
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
    private String recipient;
    private Boolean accepted;
    private String rider;
    private String driver;
    private boolean rideType;

    private boolean authorConfirmation;
    private boolean recipientConfirmation;
    private boolean isCompleted;

    /**
     * Ride Constructor
     */
    public Ride()
    {
        this.key = null;
        this.date = null;
        this.startLoc = null;
        this.destLoc = null;
        this.author = null;
        this.recipient = null;
        this.accepted = null;
        this.driver = null;
        this.rider = null;
        this.rideType = false;
        this.authorConfirmation = false;
        this.recipientConfirmation = false;
        this.isCompleted = false;
    }


    public Ride(Date date, String startLoc, String destLoc, Boolean accepted, Boolean rideType) {
        this.key = null;
        this.date = date;
        this.startLoc = startLoc;
        this.destLoc = destLoc;
        this.author = null;
        this.recipient = null;
        this.accepted = accepted;
        this.rider = null;
        this.driver = null;
        this.rideType = rideType;
        this.authorConfirmation = false;
        this.recipientConfirmation = false;
        this.isCompleted = false;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Boolean getAccepted() { return accepted; }
    public void setAccepted(Boolean accepted) {this.accepted = accepted; }

    public String getDriver() {
        return driver;
    }

    public String getRider() {
        return rider;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public void setRideType(boolean rideType) {
        this.rideType = rideType;
    }

    public boolean isRideType() {
        return rideType;
    }

    public boolean isAuthorConfirmation() {
        return authorConfirmation;
    }

    public boolean isRecipientConfirmation() {
        return recipientConfirmation;
    }

    public void setAuthorConfirmation(boolean authorConfirmation) {
        this.authorConfirmation = authorConfirmation;
    }

    public void setRecipientConfirmation(boolean recipientConfirmation) {
        this.recipientConfirmation = recipientConfirmation;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

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
