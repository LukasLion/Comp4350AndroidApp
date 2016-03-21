package com.umanitoba.comp4350androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conference_layout);

        TextView conferenceName = (TextView) findViewById(R.id.conferenceName);
        TextView when = (TextView) findViewById(R.id.when);
        TextView where = (TextView) findViewById(R.id.where);
        TextView content = (TextView) findViewById(R.id.content);
        TextView contact = (TextView) findViewById(R.id.contact);

        Bundle bundle = getIntent().getExtras();
        Conference conference = bundle.getParcelable("Conference");
        conferenceName.setText(conference.getTitle());
        when.setText(conference.getDate());
        where.setText(conference.getLocation());
        content.setText(conference.getContent());
        contact.setText("Contact " + conference.getFirstname() + " " + conference.getLastname() + " for more information");
    }
}
