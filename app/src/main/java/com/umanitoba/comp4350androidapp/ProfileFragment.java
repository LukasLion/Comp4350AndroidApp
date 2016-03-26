package com.umanitoba.comp4350androidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private Profile profile;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_layout, container, false);
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
        return view;
    }
}
