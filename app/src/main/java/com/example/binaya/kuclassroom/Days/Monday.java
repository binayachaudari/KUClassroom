package com.example.binaya.kuclassroom.Days;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binaya.kuclassroom.JSON.JsonDatabase;
import com.example.binaya.kuclassroom.R;

/**
 * Created by Binaya on 7/12/17.
 */

public class Monday extends Fragment {
    private static final String TAG = "Monday";

    TextView display;
    JsonDatabase jsonData;
    StringBuffer sb;
    String depart, year, sem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monday,container,false);
        display = (TextView) view.findViewById(R.id.JsonData);

        //Getting String Data from Setting window
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        depart = prefs.getString(getString(R.string.departments),"CS");
        year = prefs.getString(getString(R.string.year),"1st");
        sem = prefs.getString(getString(R.string.sem),"1st");

        jsonData = new JsonDatabase(this.getActivity());

        sb = new StringBuffer();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFormDatabase();
    }

    public void getDataFormDatabase(){
        Cursor result = jsonData.DisplayJSONData(TAG, depart, year, sem);
        if (result!= null && result.getCount()>0){
            while(result.moveToNext()){
                sb.append("Subject: "+result.getString(0)+"\n");
                sb.append("Lecturer: "+result.getString(1)+"\n");
                sb.append("Time: "+result.getString(3)+" - "+result.getString(4)+"\n\n");
            }
            display.setText(sb.toString());
        }
        else
            display.setText("NO CLASSES!!");
    }
}
