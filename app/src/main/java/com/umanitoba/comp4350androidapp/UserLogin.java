package com.umanitoba.comp4350androidapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserLogin {
    private HashMap<String, String> postJSONMap;
    private URL listenerURL;
    private Context context;
    private final String TAG = "FetchMapListTask";
    private OnUserLogin callback;

    public UserLogin(Context context, OnUserLogin callback){
        postJSONMap = new HashMap<String, String>();
        this.callback = callback;
        this.context = context;
        try {
            listenerURL = new URL("http://ec2-52-37-252-126.us-west-2.compute.amazonaws.com/Token");
        }
        catch(MalformedURLException e){
            Log.e(TAG, "Listener URL malformed:" + e.getMessage());
        }
    }

    public void createUserLogin(final String password, final String username){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest req = new StringRequest(Request.Method.POST, listenerURL.toString(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            // this is the relevant method
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("grant_type", "password");
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
/*
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, listenerURL.toString(), obj,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        ProcessUserLogin procArray = new ProcessUserLogin();
                        procArray.execute(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null)
                            error.printStackTrace();
                            String errormsg = error.networkResponse.data.toString();
                    }
                }
        ) { @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded; charset=utf-8";
        }};*/
        //{
            //@Override
            //public Map<String, String> getHeaders() throws AuthFailureError {
            //    Map<String, String> params = new HashMap<String, String>();
            //    //params.put("Authorization", "Bearer " + accessToken);

            //    return params;
            //}
        //};
        req.setRetryPolicy(new DefaultRetryPolicy(1000,2,2));
        queue.add(req);

    }

    private class ProcessUserLogin extends AsyncTask<JSONObject, Void, Void>{
        @Override
        protected Void doInBackground(JSONObject... params) {
            try {
                Log.i(TAG, params[0].toString());
                JSONObject userToken = params[0].getJSONObject("access_token");
                String stringToken = userToken.getString("value");
                callback.userLoginSuccessful(stringToken);
            }
            catch(JSONException e) {

            }
            return null;
        }
    }
}

