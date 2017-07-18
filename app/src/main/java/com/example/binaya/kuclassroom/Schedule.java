package com.example.binaya.kuclassroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.binaya.kuclassroom.Days.Friday;
import com.example.binaya.kuclassroom.Days.Monday;
import com.example.binaya.kuclassroom.Days.Sunday;
import com.example.binaya.kuclassroom.Days.Thursday;
import com.example.binaya.kuclassroom.Days.Tuesday;
import com.example.binaya.kuclassroom.Days.Wednesday;
import com.example.binaya.kuclassroom.NoticeTab.NewsEvents;
import com.example.binaya.kuclassroom.NoticeTab.NoticeAnnouncement;
import com.example.binaya.kuclassroom.NoticeTab.SeminarTalks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Binaya on 7/10/17.
 */

public class Schedule extends Fragment {

    //It is a referenced variable of inner class SectionsPagerAdapter, which extends the FragmentPagerAdapter class
    private SectionsPagerAdapter mSectionsPagerAdapter;

    // ViewPager -> Layout manager that allows the user to flip left and right through pages of data.
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule,container, false);

//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container_schedule);
        setupViewPager(mViewPager);                    //Calls setupViewPager function, passes mViewPager as parameter.

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }


    //PageAdapter, that represents each page as a fragment
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager());
        adapter.addFragment(new Sunday(), "Sunday");
        adapter.addFragment(new Monday(), "Monday");
        adapter.addFragment(new Tuesday(), "Tuesday");
        adapter.addFragment(new Wednesday(), "Wednesday");
        adapter.addFragment(new Thursday(), "Thursday");
        adapter.addFragment(new Friday(), "Friday");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Schedule");

    }
}

