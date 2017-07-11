package com.example.binaya.kuclassroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Binaya on 7/10/17.
 */

public class Attendance extends Fragment {
    private static final String TAG = "MainActivity";
    TextView InputDesc;
    TextView CalcDesc;
    TextView Answer;

    Button Calculate;

    EditText Credit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendance,container, false);

        InputDesc = (TextView) view.findViewById(R.id.Input_Desc);
        CalcDesc = (TextView) view.findViewById(R.id.calDesc);
        Answer = (TextView) view.findViewById(R.id.Ans);

        Calculate = (Button) view.findViewById(R.id.calc);

        Credit = (EditText) view.findViewById(R.id.input);

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getCredit;
                double getValue = 0;
                int miss = 0;
                int TotalHour=0;
                try {
                    getCredit = Integer.parseInt(Credit.getText().toString());
                    if(getCredit>0 && getCredit<=6) {
                        TotalHour = 16 * getCredit;
                        getValue = Double.parseDouble(new DecimalFormat("#.##").format(0.2 * TotalHour));
                        miss = (int) getValue;      //Converting to int value (Alternative, Integer = getValue.intValue();)
                        CalcDesc.setText("You can miss ");
                        Answer.setText(""+ miss +" lecture hours out of " + TotalHour + " hours!");
                    }
                    else{
                        CalcDesc.setText("");
                        Answer.setText("Kathmandu University doesn't have any subject whose course credit is greater than 6!");
                    }
                }
                catch(NumberFormatException ex){
                    Toast.makeText(getActivity(), "Provide Total Credit of Subject!", Toast.LENGTH_SHORT).show();
                    CalcDesc.setText("");
                    Answer.setText("Provide Total Credit of Subject to Calculate!");
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Attendance");
    }
}