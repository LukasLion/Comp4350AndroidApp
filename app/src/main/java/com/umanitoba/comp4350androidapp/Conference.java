package com.umanitoba.comp4350androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zunaid on 2016-03-19.
 */
public class Conference implements Parcelable{

    private int conferenceId;
    private String content;
    private String date;
    private String firstname;
    private String lastname;
    private String location;
    private int profileId;
    private String title;

    public Conference(int conferenceId, String content, String date, String firstname, String lastname, String location, int profileId, String title) {
        this.conferenceId = conferenceId;
        this.content = content;
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.profileId = profileId;
        this.title = title;
    }

    public Conference(Parcel in){

        conferenceId = in.readInt();
        content = in.readString();
        date = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        location = in.readString();
        profileId = in.readInt();
        title = in.readString();
    }


    public static final Creator<Conference> CREATOR = new Creator<Conference>() {
        @Override
        public Conference createFromParcel(Parcel in) {
            return new Conference(in);
        }

        @Override
        public Conference[] newArray(int size) {
            return new Conference[size];
        }
    };

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(conferenceId);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(location);
        dest.writeInt(profileId);
        dest.writeString(title);
    }
}
