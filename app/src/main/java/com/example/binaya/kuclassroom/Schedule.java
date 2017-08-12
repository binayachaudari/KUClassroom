package com.example.binaya.kuclassroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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

/**
 * Created by Binaya on 7/10/17.
 */

public class Schedule extends Fragment {

    //It is a referenced variable of inner class SectionsPagerAdapter, which extends the FragmentPagerAdapter class
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //Floating Action Button
    FloatingActionButton fab_plus, fab_not, fab_def, fab_cal, fab_attendace;
    boolean isOpen = false;

    Fragment fragment;

    // ViewPager -> Layout manager that allows the user to flip left and right through pages of data.
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule,container, false);



        fab_plus = (FloatingActionButton) view.findViewById(R.id.fab_plus);
        fab_def = (FloatingActionButton) view.findViewById(R.id.fab_def);
        fab_cal = (FloatingActionButton) view.findViewById(R.id.fab_cal);
        fab_not = (FloatingActionButton) view.findViewById(R.id.fab_not);
        fab_attendace = (FloatingActionButton) view.findViewById(R.id.fab_attendance);

        final LinearLayout notice = (LinearLayout) view.findViewById(R.id.notice);
        final LinearLayout attendance = (LinearLayout) view.findViewById(R.id.attendance);
        final LinearLayout gpa = (LinearLayout) view.findViewById(R.id.GPA);
        final LinearLayout deflection = (LinearLayout) view.findViewById(R.id.Deflection);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {


                    fab_not.setVisibility(View.GONE);
                    fab_def.setVisibility(View.GONE);
                    fab_cal.setVisibility(View.GONE);
                    fab_attendace.setVisibility(View.GONE);
                    gpa.setVisibility(View.GONE);
                    notice.setVisibility(View.GONE);
                    deflection.setVisibility(View.GONE);
                    attendance.setVisibility(View.GONE);

                    isOpen = false;
                } else {

                    fab_not.setVisibility(View.VISIBLE);
                    fab_def.setVisibility(View.VISIBLE);
                    fab_cal.setVisibility(View.VISIBLE);
                    fab_attendace.setVisibility(View.VISIBLE);
                    gpa.setVisibility(View.VISIBLE);
                    notice.setVisibility(View.VISIBLE);
                    deflection.setVisibility(View.VISIBLE);
                    attendance.setVisibility(View.VISIBLE);

                    isOpen = true;
                }


                fab_def.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isOpen = false;

                        fragment = new DeflectionCalculator();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_main, fragment);
                            ft.commit();
                        }
                    }

                });
                fab_not.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isOpen = false;

                        fragment = new Notice();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_main, fragment);
                            ft.commit();
                        }
                    }
                });
                fab_cal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isOpen = false;


                        fragment = new GPA_Calculator();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_main, fragment);
                            ft.commit();
                        }
                    }
                });
                fab_attendace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isOpen = false;

                       fragment = new Attendance();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_main, fragment);
                            ft.commit();
                        }
                    }
                });

            }

        });

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
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Schedule");

    }
}

