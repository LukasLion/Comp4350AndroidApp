package com.umanitoba.comp4350androidapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private Profile profile;
    private Profile currUser;

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("Profile", profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = (Profile) getArguments().getSerializable("Profile");
            System.out.println(profile.toString());
        }
    }

    View.OnClickListener followListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            final String url = "http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/api/FollowService";
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            getProfile();

            if(currUser != null) {
                String followerId =  Integer.toString(currUser.getProfileId());
                String followingId = Integer.toString(profile.getProfileId());
                Map<String, String> params = new HashMap<String, String>();
                params.put("followerId", followerId);
                params.put("followingId", followingId);

                JSONObject jsonBody = new JSONObject(params);
                // Request a string response
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                // Add the request to the queue
                queue.add(jsonRequest);
            }
        }
    };

    Response.Listener<String> getCurrUserListener= new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                Profile p = null;
                try{
                    p = new Profile(Integer.parseInt(object.getString("ProfileId")), object.getString("UserId"), Integer.parseInt(object.getString("Age")), object.getString("City"), object.getString("Country"), object.getString("Degree"), object.getString("FirstName"), object.getString("LastName"),object.getString("School"));
                }
                catch (NumberFormatException exception){
                    p = new Profile(Integer.parseInt(object.getString("ProfileId")), object.getString("UserId"), 200, object.getString("City"), object.getString("Country"), object.getString("Degree"), object.getString("FirstName"), object.getString("LastName"),object.getString("School"));
                }
                currUser = p;

            } catch(JSONException e){
                System.out.println(e.toString());
            }

        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        final Button button = (Button) view.findViewById(R.id.followButton);
        TextView profileName = (TextView) view.findViewById(R.id.name);
        TextView school = (TextView) view.findViewById(R.id.school);
        TextView degree = (TextView) view.findViewById(R.id.degree);
        TextView age = (TextView) view.findViewById(R.id.age);
        TextView city = (TextView) view.findViewById(R.id.city);
        TextView country = (TextView) view.findViewById(R.id.country);

        profileName.setText(profile.getFirstName() + " " + profile.getLastName());
        school.setText(profile.getSchool());
        degree.setText(profile.getDegree());
        age.setText(" "+ profile.getAge());
        city.setText(profile.getCity());
        country.setText(profile.getCountry());

        button.setOnClickListener(followListener);
        return view;
    }

    public void getProfile(){
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String url = "http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/api/ProfilesService/GetProfileByUserId/"
                + preferences.getString("UserID", null);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, getCurrUserListener,
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        // Add the request to the queue
        Request<String> queue = Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }
}
