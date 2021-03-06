package com.umanitoba.comp4350androidapp;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTabsFragment extends Fragment implements TabHost.OnTabChangeListener{

    private FragmentTabHost mTabHost;
    private String currentTab = "tab0";
    private String previousTab;

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
                ProfileList.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Message", null),
                ConversationsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Conference", null),
                ConferenceList.class, null);

        setHasOptionsMenu(true);

        mTabHost.setOnTabChangedListener(this);

        return mTabHost;
    }

    public FragmentTabHost getTabHost(){
        return mTabHost;
    }

    public Fragment currentTab(){
        if (mTabHost.getCurrentTab() == 0){
            return (ProfileList)getChildFragmentManager().findFragmentByTag("tab0");
        }
        else if (mTabHost.getCurrentTab() == 2){
            return (ConferenceList)getChildFragmentManager().findFragmentByTag("tab2");
        }
        return null;
    }

    @Override
    public void onTabChanged(String tabId) {
        previousTab = currentTab;
        currentTab = tabId;
        if (previousTab.equals("tab0")){
            ProfileList profileList = (ProfileList)getChildFragmentManager().findFragmentByTag("tab0");
            if (profileList.getFragmentManager().getBackStackEntryCount() > 0);
                boolean popped = profileList.getFragmentManager().popBackStackImmediate();
            profileList.detachFromManager();
        }
        else if (previousTab.equals("tab1")){
            //ProfileList profileList = (ProfileList)getFragmentManager().findFragmentByTag("tab0");
            //profileList.getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else if (previousTab.equals("tab2")){
            ConferenceList conferenceList = (ConferenceList)getChildFragmentManager().findFragmentByTag("tab2");
            if (conferenceList.getFragmentManager().getBackStackEntryCount() > 0);
                boolean popped = conferenceList.getFragmentManager().popBackStackImmediate();
            conferenceList.detachFromManager();
        }
    }
}
