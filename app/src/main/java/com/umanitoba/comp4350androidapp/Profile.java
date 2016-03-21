package com.umanitoba.comp4350androidapp;

/**
 * Created by zunaid on 2016-03-21.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zunaid on 2016-03-20.
 */
public class Profile implements Parcelable, Serializable {
    private int profileId;
    private String userId;
    private int age;
    private String city;
    private String country;
    private String degree;
    private String firstName;
    private String lastName;
    private String school;


    public Profile(int profileId, String userId, int age, String city, String country, String degree, String firstName, String lastName, String school) {
        this.setProfileId(profileId);
        this.setUserId(userId);
        this.setAge(age);
        this.setCity(city);
        this.setCountry(country);
        this.setDegree(degree);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setSchool(school);
    }

    public Profile(Parcel in){

        profileId = in.readInt();
        userId = in.readString();
        age = in.readInt();
        city = in.readString();
        country = in.readString();
        degree = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        school = in.readString();
    }


    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(profileId);
        dest.writeInt(age);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(degree);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(school);
        dest.writeString(userId);
    }
}