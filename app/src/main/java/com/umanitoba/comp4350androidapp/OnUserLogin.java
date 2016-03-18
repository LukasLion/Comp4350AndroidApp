package com.umanitoba.comp4350androidapp;

public interface OnUserLogin {
    public void userLoginSuccessful(String userToken, String userid);
    public void userLoginFailed(String errorMessage);
}
