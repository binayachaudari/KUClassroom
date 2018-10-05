package com.example.binaya.kuclassroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.binaya.kuclassroom.Days.Friday;
import com.example.binaya.kuclassroom.Days.Monday;
import com.example.binaya.kuclassroom.Days.Sunday;
import com.example.binaya.kuclassroom.Days.Thursday;
import com.example.binaya.kuclassroom.Days.Tuesday;
import com.example.binaya.kuclassroom.Days.Wednesday;

import java.util.Calendar;

/**
 * Created by Binaya on 7/10/17.
 */

public class Schedule extends Fragment {

    // ViewPager -> Layout manager that allows the user to flip left and right through pages of data.
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container_schedule);
        setupViewPager(mViewPager); //Calls setupViewPager function, passes mViewPager as parameter.

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        displayToday();

        return view;
    }


    //PageAdapter, that represents each page as a fragment
    private void setupViewPager(ViewPager viewPager) {
        //It is a referenced variable of inner class SectionsPagerAdapter, which extends the FragmentPagerAdapter class
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager());
        adapter.addFragment(new Sunday(), "Sunday");
        adapter.addFragment(new Monday(), "Monday");
        adapter.addFragment(new Tuesday(), "Tuesday");
        adapter.addFragment(new Wednesday(), "Wednesday");
        adapter.addFragment(new Thursday(), "Thursday");
        adapter.addFragment(new Friday(), "Friday");
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    public void displayToday(){
        Calendar calendar = Calendar.getInstance();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayPosition < 7) {
            mViewPager.setCurrentItem(dayPosition - 1);
        }
        else {
            mViewPager.setCurrentItem(0);
        }
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Schedule");

    }
}

