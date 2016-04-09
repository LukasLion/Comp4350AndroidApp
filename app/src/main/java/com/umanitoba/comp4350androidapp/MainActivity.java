package com.umanitoba.comp4350androidapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.support.v13.app.FragmentTabHost;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private String userToken = "empty";
    private String userEmail = "empty";
    private String userID = "empty";
    private boolean hasUser = false;
    private int profileID;
    private String currentFragmentName;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        hasUser = preferences.getBoolean("UserSignedIn", false);
        if (hasUser){
            userEmail = preferences.getString("UserEmail", "empty");
            userToken = preferences.getString("UserToken", "empty");
            userID = preferences.getString("UserID", "empty");
        }

        if (userID.equals("empty") || userToken.equals("empty"))
            hasUser = false;

        if (!hasUser){
            launchLogin();
        }
        else{
            launchMainTabs();
        }
    }

    //Activity Start
    @Override
    protected void onStart() {
        super.onStart();
    }

    //Activity Stop
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_signout) {
            signOut();
            launchLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if (currentFragmentName.equals("tabs")){
            FragmentTabHost fragmentTabHost = ((MainTabsFragment)currentFragment).getTabHost();
            if (fragmentTabHost.getCurrentTab() == 0) {
                ProfileList profileList = (ProfileList)((MainTabsFragment)currentFragment).currentTab();
                if (profileList.getFragmentManager().getBackStackEntryCount() > 0)
                    profileList.getFragmentManager().popBackStackImmediate();
            }
            if (fragmentTabHost.getCurrentTab() == 1) {
                ProfileList profileList = (ProfileList)((MainTabsFragment)currentFragment).currentTab();
                profileList.getFragmentManager().popBackStackImmediate();
            }
            if (fragmentTabHost.getCurrentTab() == 2) {
                ConferenceList conferenceList = (ConferenceList)((MainTabsFragment)currentFragment).currentTab();
                if (conferenceList.getFragmentManager().getBackStackEntryCount() > 0)
                    conferenceList.getFragmentManager().popBackStackImmediate();
            }
        }
        else{
            super.onBackPressed();
        }
    }

    public boolean hasUser(){
        return hasUser;
    }

    public String getUserID() { return userID; }

    public String getUserToken() { return userToken; }

    public void signOut(){
        hasUser = false;
        userToken = "empty";
        userEmail = "empty";
        userID = "empty";
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        preferenceEditor.putString("UserEmail", userEmail);
        preferenceEditor.putString("UserToken", userToken);
        preferenceEditor.putBoolean("UserSignedIn", false);
        preferenceEditor.putString("UserID", userID);
        preferenceEditor.apply();
    }

    public void launchLogin(){
        UserAccountFragment userAccountFrag = new UserAccountFragment();
        currentFragmentName = "login";
        currentFragment = userAccountFrag;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, userAccountFrag).commit();
    }

    public void launchMainTabs(){
        MainTabsFragment tabsFragment = new MainTabsFragment();
        currentFragmentName = "tabs";
        currentFragment = tabsFragment;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, tabsFragment).commit();
    }

    public void userSignedIn(String userEmail, String userToken, boolean hasUser){
        this.userEmail = userEmail;
        this.userToken = userToken;
        this.hasUser = hasUser;
        MainTabsFragment tabsFragment = new MainTabsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, tabsFragment).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }



}