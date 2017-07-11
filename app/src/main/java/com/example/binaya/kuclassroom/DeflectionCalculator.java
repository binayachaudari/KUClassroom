package com.example.binaya.kuclassroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Binaya on 7/10/17.
 */

public class DeflectionCalculator extends Fragment {
    private static final String TAG = "MainActivity";

    public static RadioGroup radio_g;
    public static RadioButton total_marks;
    public static Button calc_btn;
    int total_mark, obtained_mark;

    ScrollView scroll;

    EditText obtained_marks;

    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;
    TextView Text5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deflection, container, false);
        radio_g = (RadioGroup) view.findViewById(R.id.Radiogroup);
        calc_btn = (Button) view.findViewById(R.id.calculate_btn);
        obtained_marks = (EditText) view.findViewById(R.id.internal_marks);

        scroll = (ScrollView) view.findViewById(R.id.scroll);
        scroll.setVisibility(View.GONE);

        Text1 = (TextView) view.findViewById(R.id.textView3);
        Text2 = (TextView) view.findViewById(R.id.textView4);
        Text3 = (TextView) view.findViewById(R.id.textView5);
        Text4 = (TextView) view.findViewById(R.id.textView6);
        Text5 = (TextView) view.findViewById(R.id.textView7);

        int selected_id = radio_g.getCheckedRadioButtonId();
        total_marks = (RadioButton) view.findViewById(selected_id);

        calc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int invalid = 0;           //to check if the value entered is within the allowed value
                int empty = 0;          //to check if the edit text is empty

                TextView[] Text ={Text1,Text2,Text3,Text4,Text5};

                //variable for calculation of deflection related values
                float ratio=0;
                int pass_marks = 0;         //pass marks in final
                float internal_percentage=0;
                float deflect_percentage=0;
                float min_deflect_save=0;   //min marks to be safe from deflection
                float Min_Grade = 0;           //min grade that can be obtained without deflection

                scroll.setVisibility(View.VISIBLE);

                try{
                    obtained_mark = Integer.parseInt(obtained_marks.getText().toString()); //Convert String to Float
                    total_mark = Integer.parseInt(total_marks.getText().toString());
                    if(obtained_mark > total_mark || obtained_mark < 0){
                        invalid = 1; //check for Marks>100 or Mars<0
                        Toast.makeText(getActivity(), "Error: Mark cannot be Greater than "+ total_mark, Toast.LENGTH_SHORT).show();
                        if(obtained_mark < 0)
                            invalid = 2;
                            Toast.makeText(getActivity(), "Error: Marks Cannot be Less than 0", Toast.LENGTH_SHORT).show();
                    }

                }catch(NumberFormatException ex){
                    empty= 1;
                    Toast.makeText(getActivity(), "Input Marks!", Toast.LENGTH_SHORT).show();
                }

                //min marks calculation
                if(obtained_mark >= 8 && obtained_mark <= total_mark) {
                    ratio = (float) obtained_mark / total_mark;
                    internal_percentage = ratio * 100;
                    deflect_percentage = internal_percentage - 25;
                    min_deflect_save = (deflect_percentage / 100) * (100 - total_mark);

                    DecimalFormat df = new DecimalFormat("#.##");
                    min_deflect_save = Float.parseFloat(df.format(min_deflect_save));

                    Min_Grade = min_deflect_save + obtained_mark;
                }

                //checking pass marks of final w.r.t internal full marks
                if(total_mark == 25){
                    pass_marks = 30;
                }
                else{
                    pass_marks = 20;    //if total_marks == 50
                }

                //Setting TextView according to conditions!
                if(invalid > 0 || empty == 1){
                    if(invalid == 1)
                        Text1.setText("Error: Mark cannot be Greater than "+ total_mark);
                    if(invalid == 2)
                        Text1.setText("Error: Marks Cannot be Less than 0");
                    if(empty == 1)
                        Text1.setText("Input Marks!");
                    Text2.setText("");
                    Text3.setText("");
                    Text4.setText("");
                    Text5.setText("");
                }

                else {
                    Text1.setText("Minimum marks required to pass in final: " + pass_marks);
                    Text2.setText("Minimum marks in final to avoid deflection: " + min_deflect_save);
                    Text3.setText("Minimum grade that maybe obtained without deflection: " + getGrades(Min_Grade));
                    Text4.setText("Minimum grade that maybe obtained after deflection: ");
                    Text5.setText("Minimum marks required out of " + (100 - total_mark) + " to get an A: " + (80 - obtained_mark));
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Deflection Calculator");
    }

    public String getGrades(float Obtained){
        if(Obtained >= 80)
            return "A";
        if (Obtained >= 75)
            return "A-";
        if (Obtained >= 70)
            return "B+";
        if (Obtained >= 65)
            return "B";
        if (Obtained >= 60)
            return "B-";
        if (Obtained >= 55)
            return "C+";
        if (Obtained >= 50)
            return "C";
        if (Obtained >= 45)
            return "C-";
        if (Obtained >= 40)
            return "D";
        return "F";
    }
}
