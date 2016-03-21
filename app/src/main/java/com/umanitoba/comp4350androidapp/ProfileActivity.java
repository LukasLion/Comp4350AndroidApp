package com.umanitoba.comp4350androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        TextView profileName = (TextView) findViewById(R.id.name);
        TextView school = (TextView) findViewById(R.id.school);
        TextView degree = (TextView) findViewById(R.id.degree);
        TextView age = (TextView) findViewById(R.id.age);
        TextView city = (TextView) findViewById(R.id.city);
        TextView country = (TextView) findViewById(R.id.country);

        Bundle bundle = getIntent().getExtras();
        Profile profile = (Profile) bundle.getSerializable("Profile");
        System.out.println(profile.toString());
        profileName.setText(profile.getFirstName() + " " + profile.getLastName());
        school.setText(profile.getSchool());
        degree.setText(profile.getDegree());
        age.setText(" "+ profile.getAge());
        city.setText(profile.getCity());
        country.setText(profile.getCountry());
    }
}
