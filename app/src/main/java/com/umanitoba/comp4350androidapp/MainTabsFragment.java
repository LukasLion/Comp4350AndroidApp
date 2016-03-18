package com.umanitoba.comp4350androidapp;


import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTabsFragment extends Fragment {

    private FragmentTabHost mTabHost;

    public MainTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.container);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab0").setIndicator("Profile", null),
                UserProfile.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Conference", null),
                MainActivity.PlaceholderFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Message", null),
                MainActivity.PlaceholderFragment.class, null);

        return mTabHost;
    }

}
