package com.umanitoba.comp4350androidapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //Ahmed code started ------------------------------------------------//
    public ArrayList<Conference> conferenceList;
    public ArrayList<Profile> profileList;

    public void showAdapter(ArrayList<String> array) {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
    }

    public void showProfileAdapter(ArrayList<String> array) {
        ListView listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
    }

    public void getConferenceList(final ConferenceListener conferenceListener){
        final String url = "http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/api/ConferencesService";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        conferenceListener.onSuccessListener(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        // Add the request to the queue
        Request<String> queue = Volley.newRequestQueue(this).add(stringRequest);
    }

    public void getProfileList(final ProfilesListener profilesListener){
        final String url = "http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/api/ProfilesService";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        profilesListener.onSuccessListener(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        // Add the request to the queue
        Request<String> queue = Volley.newRequestQueue(this).add(stringRequest);
    }

    // Ahmed code ended.-----------------------------//


    private FragmentTabHost mTabHost;
    private String userToken = "empty";
    private String userEmail = "empty";
    private String userID = "empty";
    private boolean hasUser;
    private int profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // coded by Ahmed. this part is port generating the conference and profile list and redirecting the user to the details acitivity.

        final ListView profileView = (ListView) findViewById((R.id.listView2));
        final ListView conferenceView = (ListView) findViewById((R.id.listView));

        Button button = (Button) findViewById(R.id.getAllconferences);
        Button buttonProfiles = (Button) findViewById(R.id.getAllProfiles);

        buttonProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conferenceView.setVisibility(View.GONE);
                profileView.setVisibility(View.VISIBLE);

                ProfilesListener profilesListener = new ProfilesListener() {
                    @Override
                    public void onSuccessListener(String result) {
                        ArrayList<Profile> profiles = new ArrayList<Profile>();
                        ArrayList<String> myListView = new ArrayList<String>();

                        try {

                            JSONArray array = new JSONArray(result);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Profile profile = new Profile(Integer.parseInt(object.getString("ProfileId")), object.getString("UserId"), Integer.parseInt(object.getString("Age")), object.getString("City"), object.getString("Country"), object.getString("Degree"), object.getString("FirstName"), object.getString("LastName"),object.getString("School"));
                                profiles.add(profile);
                                myListView.add(profile.getFirstName() + " " + profile.getLastName());
                            }

                            showProfileAdapter(myListView);
                            profileList = profiles;

                        } catch(JSONException e){
                            System.out.println(e.toString());
                        }
                    }
                };

                getProfileList(profilesListener);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                profileView.setVisibility(View.GONE);
                conferenceView.setVisibility(View.VISIBLE);

                ConferenceListener conferenceListener = new ConferenceListener() {
                    @Override
                    public void onSuccessListener(String result) {

                        ArrayList<Conference> conferences = new ArrayList<Conference>();
                        ArrayList<String> myListView = new ArrayList<String>();

                        try {

                            JSONArray array = new JSONArray(result);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Conference conference = new Conference(Integer.parseInt(object.getString("ConferenceId")), object.getString("Content"), object.getString("Date"), object.getString("FirstName"), object.getString("LastName"), object.getString("Location"), Integer.parseInt(object.getString("ProfileId")),object.getString("Title"));
                                conferences.add(conference);
                                myListView.add(object.getString("Date").substring(0, 10) + "  " + object.getString("Title"));
                            }

                            showAdapter(myListView);
                            conferenceList = conferences;

                        } catch(JSONException e){
                            System.out.println(e.toString());
                        }
                    }
                };
                getConferenceList(conferenceListener);
            }
        });

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ConferenceActivity.class);
                Conference conference = conferenceList.get(position);
                intent.putExtra("Conference", conference);
                startActivity(intent);
            }
        });

        ListView lv2 = (ListView) findViewById(R.id.listView2);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                Profile profile = profileList.get(position);
                bundle.putSerializable("Profile", profile);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //code ended by Ahmed-------------------------------------------------------------//

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

    public boolean hasUser(){
        return false;
    }

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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, userAccountFrag).commit();
    }

    public void launchMainTabs(){
        MainTabsFragment tabsFragment = new MainTabsFragment();
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