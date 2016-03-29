package com.umanitoba.comp4350androidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
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


public class ConferenceList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Conference> conferenceList;
    private ArrayList<String> conferenceNames;
    private ConferenceListener conferenceListener;


    public ConferenceList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConferenceList.
     */
    // TODO: Rename and change types and number of parameters
    public static ConferenceList newInstance(String param1, String param2) {
        ConferenceList fragment = new ConferenceList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void showConferenceAdapter(ArrayList<String> array) {
        ListView listView = (ListView) getView().findViewById(R.id.conference_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            conferenceList = savedInstanceState.getParcelableArrayList("ConferenceList");
            conferenceNames = savedInstanceState.getStringArrayList("ConferenceNames");
        }
        else{
            conferenceList = null;
            conferenceNames = null;
        }

        conferenceListener = new ConferenceListener() {
            @Override
            public void onSuccessListener(String result) {
                try {

                    JSONArray array = new JSONArray(result);

                    if (conferenceList == null)
                        conferenceList = new ArrayList<Conference>();
                    else if (!conferenceList.isEmpty())
                        conferenceList.clear();

                    if (conferenceNames == null)
                        conferenceNames = new ArrayList<String>();
                    else if (!conferenceNames.isEmpty())
                        conferenceNames.clear();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Conference conference = new Conference(Integer.parseInt(object.getString("ConferenceId")), object.getString("Content"), object.getString("Date"), object.getString("FirstName"), object.getString("LastName"), object.getString("Location"), Integer.parseInt(object.getString("ProfileId")),object.getString("Title"));
                        conferenceList.add(conference);
                        conferenceNames.add(object.getString("Date").substring(0, 10) + "  " + object.getString("Title"));
                    }

                    showConferenceAdapter(conferenceNames);

                } catch(JSONException e){
                    System.out.println(e.toString());
                }
            }
        };

        getConferenceList(conferenceListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if (conferenceList != null && !conferenceList.isEmpty()){
            outState.putParcelableArrayList("ConferencList", conferenceList);
            outState.putStringArrayList("ConferenceNames", conferenceNames);
        }
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
        Request<String> queue = Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conference_list, container, false);
        ListView lv2 = (ListView) view.findViewById(R.id.conference_list_view);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conference conference = conferenceList.get(position);
                if (conference != null) {
                    ConferenceFragment conferenceFragment = new ConferenceFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Conference", conference);
                    conferenceFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, conferenceFragment).addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        if (conferenceList == null)
            getConferenceList(conferenceListener);
        return view;

    }
}
