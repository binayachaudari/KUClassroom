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
import com.example.binaya.kuclassroom.JSON.JsonDatabase;
import com.example.binaya.kuclassroom.R;

/**
 * Created by Binaya on 7/12/17.
 */

public class Thursday extends Fragment {
    private static final String TAG = "Thursday";
    TextView display;
    JsonDatabase jsonData;
    StringBuffer sb;
    String depart, year, sem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thursday,container,false);
        display = view.findViewById(R.id.JsonData);

        //Getting String Data from Setting window
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        depart = prefs.getString(getString(R.string.departments),"CS");
        year = prefs.getString(getString(R.string.year),"1st");
        sem = prefs.getString(getString(R.string.sem),"1st");

        jsonData = new JsonDatabase(this.getActivity());

        sb = new StringBuffer();
        return view;
    }

    public void getDataFormDatabase(){
        Cursor result = jsonData.DisplayJSONData(TAG, depart, year, sem);
        sb.setLength(0);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Getting String Data from Setting window
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        depart = prefs.getString(getString(R.string.departments),"CS");
        year = prefs.getString(getString(R.string.year),"1st");
        sem = prefs.getString(getString(R.string.sem),"1st");

        getDataFormDatabase();
    }

}
