package com.example.binaya.kuclassroom.Days;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binaya.kuclassroom.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.binaya.kuclassroom.JSON.*;

/**
 * Created by Binaya on 7/12/17.
 */

public class Sunday extends Fragment {
    private static final String TAG = "Sunday";

    JsonDatabase jsonData;

    JSONParser parser;
    String Data;
    String URL = "https://binayachaudari.github.io/KUScheduleFiles/IIYIIS.json";

    String Subject;
    String Lecturer;
    String Day;
    String Start;
    String End;
    String Dept;
    String Year;
    String Sem;

    String version;

    StringBuffer sb;

    TextView display;

    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sunday,container,false);

        connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        display = (TextView) view.findViewById(R.id.JsonData);

        jsonData = new JsonDatabase(this.getActivity());

        sb = new StringBuffer();

        if (networkInfo != null && networkInfo.isConnected())
            Toast.makeText(getActivity(), "Connected To Internet", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Unable to Connect To Internet", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        {
            {
                /**
                 * StrictMode is a developer tool which detects things you might be doing by accident and brings them to your attention so you can fix them.
                 * StrictMode is most commonly used to catch accidental disk or network access on the application's main thread,
                 * where UI operations are received and animations take place
                 */

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if (isFirstTime())
                    getDataFromServer();
                else
                    getDataFormDatabase();
            }
        }
    }

    public void getDataFromServer(){
        parser = new JSONParser();
        Data = parser.getJson(URL);
        if(Data != null) {
            try {
                JSONObject jsonObject = new JSONObject(Data);
                version = jsonObject.getString("version");
                JSONArray schedule = jsonObject.getJSONArray("schedule");
                for(int i=0; i<schedule.length(); i++){
                    JSONObject eachObject = schedule.getJSONObject(i);
                    Subject = eachObject.getString("subject");
                    Lecturer = eachObject.getString("lecturer");
                    Day = eachObject.getString("day");
                    Start =  eachObject.getString("start");
                    End = eachObject.getString("end");
                    Dept = eachObject.getString("dept");
                    Year = eachObject.getString("year");
                    Sem = eachObject.getString("sem");

                    jsonData.insertJSONData(Subject, Lecturer, Day , Start, End, Dept, Year ,Sem);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getDataFormDatabase();
    }

    public void getDataFormDatabase(){
        Cursor result = jsonData.DisplayJSONData(TAG);
        if (result!= null && result.getCount()>0){
            while(result.moveToNext()){
                sb.append("Subject: "+result.getString(0)+"\n");
                sb.append("Lecturer: "+result.getString(1)+"\n");
                sb.append("Time: "+result.getString(3)+" - "+result.getString(4)+"\n\n");
            }
        }
        display.setText(sb.toString());
    }

    public boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("Schedule", false);
        SharedPreferences.Editor editor = preferences.edit();
        if (!ranBefore && networkInfo != null && networkInfo.isConnected()) {
            editor.putBoolean("Schedule", true);
            editor.commit();
        }
        return !ranBefore;
    }

//    public boolean CheckUpdate(){
//        int ver = Integer.parseInt(version);
//        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        int VerCheck = pref.getInt("VersionNo",0);
//        SharedPreferences.Editor editor = pref.edit();
//        if (ver != VerCheck){
//            editor.putInt("VersionNo",ver);
//            editor.commit();
//            return true;
//        }
//        return false;
//    }
}
