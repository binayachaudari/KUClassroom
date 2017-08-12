package com.example.binaya.kuclassroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Binaya on 7/10/17.
 */

public class GPA_Calculator extends Fragment implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";
    DatabaseHelper myDb;

    int total;
    int datacount;

    Button calculate;

    String DeptSelected;
    String YearSelected;
    String SemSelected;

    TextView GPA_Display;
    TextView Grade_Display;

    TextView Subject1;
    TextView Subject2;
    TextView Subject3;
    TextView Subject4;
    TextView Subject5;
    TextView Subject6;
    TextView Subject7;
    TextView Subject8;
    TextView Subject9;
    TextView Subject10;

    EditText Mark1;
    EditText Mark2;
    EditText Mark3;
    EditText Mark4;
    EditText Mark5;
    EditText Mark6;
    EditText Mark7;
    EditText Mark8;
    EditText Mark9;
    EditText Mark10;

    ArrayList<Integer> credits;
    ArrayList<String> Subject;
    ArrayList<String> CheckGrade;

    String depart, year, sem;

    Spinner Department,Year,Semester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.gpacalculator,container, false);
        myDb = new DatabaseHelper(this.getActivity());

        GPA_Display = (TextView) view.findViewById(R.id.GPA);
        Grade_Display = (TextView) view.findViewById(R.id.GPAdesc);

        Subject1 = (TextView) view.findViewById(R.id.Subject1);
        Subject2 = (TextView) view.findViewById(R.id.Subject2);
        Subject3 = (TextView) view.findViewById(R.id.Subject3);
        Subject4 = (TextView) view.findViewById(R.id.Subject4);
        Subject5 = (TextView) view.findViewById(R.id.Subject5);
        Subject6 = (TextView) view.findViewById(R.id.Subject6);
        Subject7 = (TextView) view.findViewById(R.id.Subject7);
        Subject8 = (TextView) view.findViewById(R.id.Subject8);
        Subject9 = (TextView) view.findViewById(R.id.Subject9);
        Subject10 = (TextView) view.findViewById(R.id.Subject10);

        Mark1 = (EditText) view.findViewById(R.id.Mark1);
        Mark2 = (EditText) view.findViewById(R.id.Mark2);
        Mark3 = (EditText) view.findViewById(R.id.Mark3);
        Mark4 = (EditText) view.findViewById(R.id.Mark4);
        Mark5 = (EditText) view.findViewById(R.id.Mark5);
        Mark6 = (EditText) view.findViewById(R.id.Mark6);
        Mark7 = (EditText) view.findViewById(R.id.Mark7);
        Mark8 = (EditText) view.findViewById(R.id.Mark8);
        Mark9 = (EditText) view.findViewById(R.id.Mark9);
        Mark10 = (EditText) view.findViewById(R.id.Mark10);

        calculate = (Button) view.findViewById(R.id.calculate);

        Department = (Spinner) view.findViewById(R.id.spinner_dept);
        Year = (Spinner) view.findViewById(R.id.spinner_year);
        Semester = (Spinner) view.findViewById(R.id.spinner_sem);

        //ArrayAdapter(Context context, int resource, int textViewResourceId);
        ArrayAdapter<String> myAdapter_Department =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Departments));
        ArrayAdapter<String> myAdapter_Year =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Year));
        ArrayAdapter<String> myAdapter_Sem =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Semesters));

        myAdapter_Department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAdapter_Year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAdapter_Sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Department.setAdapter(myAdapter_Department);
        Year.setAdapter(myAdapter_Year);
        Semester.setAdapter(myAdapter_Sem);

        Department.setOnItemSelectedListener(this);
        Year.setOnItemSelectedListener(this);
        Semester.setOnItemSelectedListener(this);

        //Getting String Data from Setting window
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        depart = prefs.getString(getString(R.string.departments),"CS");
        year = prefs.getString(getString(R.string.year),"1st");
        sem = prefs.getString(getString(R.string.sem),"1st");
        int dept_select = getIndexOf(Department, depart);
        int year_select = getIndexOf(Year,year);
        int sem_select = getIndexOf(Semester,sem);
        Department.setSelection(dept_select);
        Year.setSelection(year_select);
        Semester.setSelection(sem_select);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] Marks_input = {Mark1, Mark2, Mark3, Mark4, Mark5, Mark6, Mark7, Mark8, Mark9, Mark10};
                ArrayList<Double> GradePoints = new ArrayList<Double>();
                CheckGrade = new ArrayList<String>();
                int check = 0;
                int invalid=0;
                float Mark;
                int empty=0;
                //The problem seems that when there is no value in EditText.
                //Integer parser don't know how to parse empty String, so Exception is thrown.
                try{
                    for (int m=0; m<datacount; m++){
                        Mark = Float.parseFloat(Marks_input[m].getText().toString()); //Convert String to Float
                        if(Mark <= 100 && Mark >=0) {
                            String Grade = getGrades(Mark);
                            CheckGrade.add(Grade);                                     //Add grades to ArrayList
                            GradePoints.add(getGradePoints(Grade));                     //Add GradePoint to ArrayList
//                    Log.d(TAG, "CalcGPA: "+CheckGrade);
                        }
                        else{
                            invalid = invalid + 1; //check for Marks>100 or Mars<0
                            Toast.makeText(getActivity(), "Error: Mark cannot be Greater than 100!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(NumberFormatException ex){
                    empty= empty+1;
                    Toast.makeText(getActivity(), "Input Marks For All Subject!", Toast.LENGTH_SHORT).show();
                }

                // GPA multiplication...
                double GPA=0;
                double CGPA=0;
                for (int i=0; i< GradePoints.size();i++){
                    GPA = GPA + (credits.get(i)*GradePoints.get(i));
                    CGPA = (GPA/total);
                    CGPA = Double.parseDouble(new DecimalFormat("#.##").format(CGPA));
                }

                //Check the Array of Grade if it contains any F;
                for (int j=0; j< CheckGrade.size();j++){
                    if(CheckGrade.get(j).toString() == "F"){
                        check = check +1;  //Num of subjects failed!
                    }
                }

                if (empty > 0){
                    GPA_Display.setText("");
                    Grade_Display.setText("");
                }
                if(check == 0 && invalid==0 && empty == 0) {
                    String finalGrade = getFinalGrade(CGPA);
                    //Displaying GPA and Grade in Text view..
                    GPA_Display.setText("Your CGPA is = " + CGPA);
                    Grade_Display.setText("Your Equivalent Grade is : " +finalGrade);
                }
                else if(check > 0){
                    Toast.makeText(getActivity(), "You've Failed in"+ check +" Subject!!", Toast.LENGTH_SHORT).show();
                    GPA_Display.setText("You've Failed in " + check +" Subject!!");
                    Grade_Display.setText("Your Final Grade is: F");
                }
                else if(invalid > 0){
                    GPA_Display.setText("Invalid Marks!!");
                    Grade_Display.setText("Your Grade Cannot be Calculated!");
                }
            }
        });


        if(isFirstTime()) {
            myDb.insertData();
        }
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String mSelected= parent.getItemAtPosition(position).toString();
//        Toast.makeText(this, mSelected, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "onItemSelected: "+ mSelected);

        total = 0;
        EditText[] Marks = {Mark1, Mark2, Mark3, Mark4, Mark5, Mark6, Mark7, Mark8, Mark9, Mark10};
        TextView[] Subjects ={Subject1, Subject2, Subject3, Subject4, Subject5, Subject6, Subject7, Subject8, Subject9, Subject10};
        GPA_Display.setText("");
        Grade_Display.setText("");

        //Changing the visibility of TextView and EditText;
        for (int i=0; i<Marks.length; i++){
            Marks[i].setVisibility(View.GONE);
            Marks[i].setText("");
        }

        for (int i=0; i< Subjects.length; i++){
            Subjects[i].setVisibility(View.GONE);
            Subjects[i].setText("");

        }

        switch(parent.getId()){
            case R.id.spinner_dept:
                DeptSelected = parent.getSelectedItem().toString();
//                Log.d(TAG, "onItemSelected: "+ DeptSelected);
                break;
            case R.id.spinner_year:
                YearSelected = parent.getSelectedItem().toString();
//                Log.d(TAG, "onItemSelected: "+ YearSelected);
                break;
            case R.id.spinner_sem:
                SemSelected = parent.getSelectedItem().toString();
//                Log.d(TAG, "onItemSelected: "+ SemSelected);
                break;
        }


        //GETTING DATA FROM THE DATABASE
        Cursor result = myDb.DisplayData(YearSelected, SemSelected, DeptSelected); //Any query in the SQLite database returns a Cursor object and the Cursor points to a single row.
        credits = new ArrayList<Integer>();
        Subject = new ArrayList<String>();
        datacount = result.getCount();
        if(result!=null && result.getCount()>0){
            while (result.moveToNext()){        //moves cursor to nex row
                Subject.add(result.getString(0));
                credits.add(result.getInt(1));
                total = total + result.getInt(1);
            }
//            Log.d(TAG, "onItemSelected: "+ Subject);
//            Log.d(TAG, "onItemSelected: "+ credits);
            Toast.makeText(getActivity(), "Total Credit: "+total, Toast.LENGTH_SHORT).show();

//            DisplayData.setText(stringBuffer.toString());

            for(int i = 0; i < result.getCount();i++){
                Marks[i].setVisibility(View.VISIBLE);
                Subjects[i].setVisibility(View.VISIBLE);
                Subjects[i].setText("Course Code: "+Subject.get(i)+"\n"+"Credits: "+credits.get(i));
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("GPA Calculator");
    }

    //Returns the position in array of Spinner of String from setting activity
    public int getIndexOf(Spinner spin, String mystring){
        int index = 0;
        for (int i = 0; i< spin.getCount();i++){
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(mystring)){
                index = i;
                break;
            }
        }
        return index;
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

    public double getGradePoints(String Grd){
        switch (Grd){
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;
            case "D":
                return 1.0;
        }
        return 0.0;
    }

    public String getFinalGrade(double CGrapdePoints){
        if(CGrapdePoints == 4.0)
            return "A";
        if(CGrapdePoints >= 3.7)
            return "A-";
        if(CGrapdePoints >= 3.3)
            return "B+";
        if(CGrapdePoints >= 3.0)
            return "B";
        if(CGrapdePoints >= 2.7)
            return "B-";
        if(CGrapdePoints >= 2.3)
            return "C+";
        if(CGrapdePoints >= 2.0)
            return "C";
        if(CGrapdePoints >= 1.7)
            return "C-";
        if(CGrapdePoints >= 1.0)
            return "D";
        return "F";
    }

    private boolean isFirstTime(){
        SharedPreferences preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore",false);
        if(!ranBefore){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore",true);
            editor.commit();
        }
        return !ranBefore;
    }
}

