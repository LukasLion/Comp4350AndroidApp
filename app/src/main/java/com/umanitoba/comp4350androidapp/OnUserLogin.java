package com.umanitoba.comp4350androidapp;

public interface OnUserLogin {
    public void userLoginSuccessful(String userToken);
    public void userLoginFailed();
}
