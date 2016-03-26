package com.umanitoba.comp4350androidapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment implements OnUserLogin{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean signedIn;
    private String userEmail;
    private String userPassword;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountFragment newInstance(String param1, String param2) {
        UserAccountFragment fragment = new UserAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_account, container, false);
        final Button button = (Button) view.findViewById(R.id.user_account_login_button);
        if (((MainActivity) getActivity()).hasUser()){
            button.setText("Sign Out");
        }
        else
            button.setText("Sign In");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((MainActivity) getActivity()).hasUser()) {
                    userEmail = ((EditText) view.findViewById(R.id.user_account_email_edittext)).getText().toString();
                    userPassword = ((EditText) view.findViewById(R.id.user_account_password_edittext)).getText().toString();
                    UserLogin userLogin = new UserLogin(getActivity().getApplicationContext(), UserAccountFragment.this);
                    userLogin.createUserLogin(userPassword, userEmail);
                }
                else{
                    ((MainActivity) getActivity()).signOut();
                    userEmail = null;
                    userPassword = null;
                    button.setText("Sign In");
                }
            }
        });
        view.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        return view;
    }

    @Override
    public void userLoginSuccessful(final String userToken, final String userid) {
        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) getView().findViewById(R.id.network_error_text)).setVisibility(View.INVISIBLE);
                SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor preferenceEditor = preferences.edit();
                preferenceEditor.putString("UserEmail", userEmail);
                preferenceEditor.putString("UserToken", userToken);
                preferenceEditor.putBoolean("UserSignedIn", true);
                preferenceEditor.putString("UserID", userid);
                preferenceEditor.apply();

                ((MainActivity) getActivity()).userSignedIn(userEmail, userToken, true);
            }
        });
    }

    @Override
    public void userLoginFailed(final String errorMessage) {
        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView errorText = ((TextView) getView().findViewById(R.id.network_error_text));
                errorText.setText("Login Error: " + errorMessage);
                errorText.setVisibility(View.VISIBLE);
            }
        });
    }
}
