package com.umanitoba.comp4350androidapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentTabHost;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TabHost;
import android.os.Message;

public class MainActivity extends Activity {

    private FragmentTabHost mTabHost;
    private String userToken;
    private String userEmail;
    private boolean hasUser;
    private int profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        hasUser = preferences.getBoolean("UserSignedIn", false);
        if (hasUser){
            userEmail = preferences.getString("UserEmail", null);
            userToken = preferences.getString("UserToken", null);
        }

        if (userEmail.equals(null) || userToken.equals(null))
            hasUser = false;

        hasUser=false;
        if (!hasUser){
            UserAccountFragment userAccountFrag = new UserAccountFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, userAccountFrag).commit();
        }
        else{
            MainTabsFragment tabsFragment = new MainTabsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, tabsFragment).commit();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean hasUser(){
        return false;
    }

    public void signOut(){
        hasUser = false;
        userToken = null;
        userEmail = null;
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