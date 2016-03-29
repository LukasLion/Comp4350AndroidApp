package com.umanitoba.comp4350androidapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConferenceFragment extends Fragment {

    private Conference conference;


    public static ConferenceFragment newInstance(Conference conference) {
        ConferenceFragment fragment = new ConferenceFragment();
        Bundle args = new Bundle();
        args.putSerializable("Conference", conference);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            conference = (Conference) getArguments().getSerializable("Conference");
            System.out.println(conference.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conference, container, false);

        TextView conferenceName = (TextView) view.findViewById(R.id.conferenceName);
        TextView when = (TextView) view.findViewById(R.id.when);
        TextView where = (TextView) view.findViewById(R.id.where);
        TextView contact = (TextView) view.findViewById(R.id.contact);
        TextView content = (TextView) view.findViewById(R.id.content);

        conferenceName.setText(conference.getTitle());
        when.setText(conference.getDate());
        where.setText(conference.getLocation());
        contact.setText(" "+ conference.getFirstname() + " " + conference.getLastname());
        content.setText(conference.getContent());
        return view;
    }


}
