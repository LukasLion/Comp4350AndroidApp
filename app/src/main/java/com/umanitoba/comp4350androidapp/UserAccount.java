package com.umanitoba.comp4350androidapp;

public class UserAccount {
    private String userEmail;
    private String userPassword;

    public UserAccount(String userEmail, String userPassword){
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserAccount(){

    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
}
