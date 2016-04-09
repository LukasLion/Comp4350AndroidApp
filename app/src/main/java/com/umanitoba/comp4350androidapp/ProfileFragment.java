package com.umanitoba.comp4350androidapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileFragment extends Fragment implements ProfilePicListener {

    private Profile profile;
    private final int PICK_IMAGE_REQUEST = 1;

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

        if (profile.getUserImage() >= 0) {
            getProfilePicture();
        }

        TextView profileName = (TextView) view.findViewById(R.id.name);
        TextView school = (TextView) view.findViewById(R.id.school);
        TextView degree = (TextView) view.findViewById(R.id.degree);
        TextView age = (TextView) view.findViewById(R.id.age);
        TextView city = (TextView) view.findViewById(R.id.city);
        TextView country = (TextView) view.findViewById(R.id.country);
        ImageView profileImage = (ImageView) view.findViewById(R.id.user_image);

        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                return true;
            }
        });

        profileName.setText(profile.getFirstName() + " " + profile.getLastName());
        school.setText(profile.getSchool());
        degree.setText(profile.getDegree());
        age.setText(" "+ profile.getAge());
        city.setText(profile.getCity());
        country.setText(profile.getCountry());

        return view;
    }

    public void getProfilePicture(){
        final String url = "http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/api/FilesService/GetFiles/" + profile.getUserImage();
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(response);
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
    public void onSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String array = object.getString("Content");
            byte bytes[] = Base64.decode(array, Base64.DEFAULT | Base64.NO_WRAP);

            ConnectRFile file = new ConnectRFile(object.optInt("Id"), object.optInt("ProfileId"), object.optString("FileName"), object.optString("ContentType"), bytes, object.optInt("FileType"));

            updateProfilePic(file);
        }
        catch (JSONException e){
            System.out.println(e.toString());
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void updateProfilePic(ConnectRFile file){
        ImageView profileImage = (ImageView) getView().findViewById(R.id.user_image);
        Bitmap pic = BitmapFactory.decodeByteArray(file.getContent(), 0, file.getContent().length);
        profileImage.setImageBitmap(pic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) getView().findViewById(R.id.user_image);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
