package com.example.binaya.kuclassroom.MenuActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.binaya.kuclassroom.R;

public class Settings_Activity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
