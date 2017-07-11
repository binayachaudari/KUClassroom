package com.example.binaya.kuclassroom.MenuActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.binaya.kuclassroom.R;

public class Feedback_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Feedback");
    }
}
