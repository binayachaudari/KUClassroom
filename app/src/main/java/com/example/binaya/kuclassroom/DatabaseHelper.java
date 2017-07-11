package com.example.binaya.kuclassroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Binaya on 7/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    //final: a variable who's value can't be modified after it is declared
    //static: no instance necessary to use it
    public static final String DATABASE_NAME = "Syllabus.db";
    public static final String TABLE_NAME = "Course";

    public static final String COURSE = "COURSE_ID";
    public static final String CREDIT = "CREDIT";
    public static final String YEAR = "YEAR";
    public static final String SEM = "SEMESTER";
    public static final String DEPT = "DEPARTMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (COURSE_ID TEXT, CREDIT INTEGER, YEAR TEXT, SEMESTER TEXT, DEPARTMENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor DisplayData(String Year, String Semester, String Department){
        SQLiteDatabase db = this.getReadableDatabase();
        String DisplayQuery = "SELECT * FROM "+ TABLE_NAME +" WHERE YEAR ='"+ Year +"' AND SEMESTER = '"+ Semester +"' AND DEPARTMENT ='" + Department + "'";
        Cursor result = db.rawQuery(DisplayQuery, null); //rawQuery(query,SelectionArgs)
        return result;
    }

    public boolean insertData(){
        SQLiteDatabase db = this.getWritableDatabase();
          String[] Course_id = {
                "MATH 101", "PHYS 101", "CHEM 101", "COMP 103", "EEEG 101", "ENGG 101", "EDRG 101", "ENGT 101",
                "MATH 104", "PHYS 102", "MEEG 101", "ENGG 103", "COMP 116", "ENVE 101", "ENGG 102", "EDRG 102", "ENGT 102",
                "MATH 208", "MCSC 201", "EEEG 211", "COMP 202", "EEEG 202", "COMP 206", "COMP 208",
                "MATH 207", "MCSC 202", "COMP 232", "COMP 204", "COMP 231", "COMP 207",
                "COEG 304", "MGTS 301", "COMP 301", "COMP 307", "COMP 315", "COMP 303", "COMP 310",
                "COMP 304", "COMP 302", "COMP 306", "COMP 342", "COMP 314", "COMP 341", "COMP 308",
                "COMP 401", "COMP 407", "MGTS 403", "COMP 409", "COMP 472", "Elective I",
                "MGTS 402", "Elective II", "COMP 408",

                "MATH 101", "PHYS 101", "CHEM 101", "COMP 103", "EEEG 101", "ENGG 101", "EDRG 101", "ENGT 101",
                "MATH 104", "PHYS 102", "MEEG 101", "ENGG 103", "COMP 116", "ENVE 101", "ENGG 102", "EDRG 102", "ENGT 102",
                "MATH 208", "MCSC 201", "EEEG 211", "COMP 202", "EEEG 202", "COMP 206", "COMP 208",
                "MATH 207", "MCSC 202", "COMP 232", "COMP 204", "COMP 231", "COMP 207",
                "COMP 317", "COMP 342", "MGTS 301", "COMP 315", "COMP 316", "COMP 307", "COMP 311",
                "MATH 322", "COMP 409", "COMP 302", "COMP 314", "COMP 323", "COMP 341", "COMP 313",
                "COMP 401", "MGTS 403", "COMP 472", "Elective I", "Elective II",
                "MGTS 402", "COMP 486","COMP 408"
        };

        String[] Credit = {
                "3", "3", "3", "2", "3", "2", "2", "2",
                "3", "3", "2", "1", "3", "2", "2", "2", "2",
                "3", "3", "3", "3", "3", "2", "1",
                "4", "3", "3", "3", "3", "2",
                "3", "3", "3", "3", "3", "2", "1",
                "3", "3", "3", "3", "3", "3", "1",
                "3", "3", "3", "3", "3", "3",
                "3", "3", "6",

                "3", "3", "3", "2", "3", "2", "2", "2",
                "3", "3", "2", "1", "3", "2", "2", "2", "2",
                "3", "3", "3", "3", "3", "2", "1",
                "4", "3", "3", "3", "3", "2",
                "3", "3", "3", "3", "3", "3", "2",
                "3", "3", "3", "3", "3", "3", "2",
                "3", "3", "3", "3", "3",
                "3", "3", "6"
        };

        String[] Year= {
                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "3rd", "3rd", "3rd", "3rd", "3rd", "3rd", "3rd",
                "3rd", "3rd", "3rd", "3rd", "3rd", "3rd", "3rd",
                "4th", "4th", "4th", "4th", "4th", "4th",
                "4th", "4th", "4th",


                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "3rd", "3rd", "3rd", "3rd", "3rd", "3rd", "3rd",
                "3rd", "3rd", "3rd", "3rd", "3rd", "3rd", "3rd",
                "4th", "4th", "4th", "4th", "4th",
                "4th", "4th", "4th"
        };

        String[] Sem= {
                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd",

                "1st", "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd", "2nd", "2nd", "2nd", "2nd",
                "1st", "1st", "1st", "1st", "1st",
                "2nd", "2nd", "2nd"
        };

        String[] Dept= {
                "CE", "CE", "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE", "CE", "CE", "CE",
                "CE", "CE", "CE",

                "CS", "CS", "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS", "CS", "CS",
                "CS", "CS", "CS"
        };

        //This class is used to store a set of values
        ContentValues contentValues = new ContentValues(); //Creates an empty set of values using the default initial size
        for(int i=0;i<Course_id.length;i++) {
            contentValues.put(COURSE, Course_id[i]);
            contentValues.put(CREDIT, Credit[i]);
            contentValues.put(YEAR, Year[i]);
            contentValues.put(SEM, Sem[i]);
            contentValues.put(DEPT, Dept[i]);
            db.insert(TABLE_NAME, null, contentValues);
        }

        return true;
    }
}
