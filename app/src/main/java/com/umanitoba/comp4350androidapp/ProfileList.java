package com.umanitoba.comp4350androidapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Profile> profileList;
    private ArrayList<String> profileNames;
    private ProfilesListener profilesListener;


    public ProfileList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileList.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileList newInstance(String param1, String param2) {
        ProfileList fragment = new ProfileList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void showProfileAdapter(ArrayList<String> array) {
        ListView listView = (ListView) getView().findViewById(R.id.profile_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profileList = savedInstanceState.getParcelableArrayList("ProfileList");
            profileNames = savedInstanceState.getStringArrayList("ProfileNames");
        }
        else{
            profileList = null;
            profileNames = null;
        }

        profilesListener = new ProfilesListener() {
            @Override
            public void onSuccessListener(String result) {
                try {

                    JSONArray array = new JSONArray(result);

                    if (profileList == null)
                        profileList = new ArrayList<Profile>();
                    else if (!profileList.isEmpty())
                        profileList.clear();

                    if (profileNames == null)
                        profileNames = new ArrayList<String>();
                    else if (!profileNames.isEmpty())
                        profileNames.clear();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Profile profile = new Profile(object.optInt("ProfileId"), object.getString("UserId"), object.optInt("Age", 200), object.getString("City"), object.getString("Country"), object.getString("Degree"), object.getString("FirstName"), object.getString("LastName"),object.getString("School"), object.optInt("UserImage", -1));
                        profileList.add(profile);
                        profileNames.add(profile.getFirstName() + " " + profile.getLastName());
                    }

                    showProfileAdapter(profileNames);

                } catch(JSONException e){
                    System.out.println(e.toString());
                }
            }
        };
        getProfileList(profilesListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilelist, container, false);
        ListView lv2 = (ListView) view.findViewById(R.id.profile_list_view);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile profile = profileList.get(position);
                if (profile != null) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Profile", profile);
                    profileFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction().add(R.id.container, profileFragment).addToBackStack(null);
                    transaction.detach(ProfileList.this);
                    transaction.commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (profileList == null)
            getProfileList(profilesListener);
        else {
            showProfileAdapter(profileNames);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if (profileList != null && !profileList.isEmpty()){
            outState.putParcelableArrayList("ProfileList", profileList);
            outState.putStringArrayList("ProfileNames", profileNames);
        }
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
        Request<String> queue = Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }

    public void detachFromManager(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(this);
        transaction.commit();
    }

}
