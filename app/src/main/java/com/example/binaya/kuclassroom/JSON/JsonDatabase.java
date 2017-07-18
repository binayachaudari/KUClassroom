package com.example.binaya.kuclassroom.JSON;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Binaya on 7/17/17.
 */

public class JsonDatabase extends SQLiteOpenHelper {
    //final: a variable who's value can't be modified after it is declared
    //static: no instance necessary to use it
    public static final String DATABASE_NAME = "Json.db";
    public static final String TABLE_SCHEDULE = "Schedule";

    public static final String YEAR = "YEAR";
    public static final String SEM = "SEMESTER";
    public static final String DEPT = "DEPARTMENT";
    public static final String SUBJECT = "SUBJECT";
    public static final String LECTURER = "LECTURER";
    public static final String START = "START";
    public static final String END = "END";
    public static final String DAY = "DAY";

    public JsonDatabase (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SCHEDULE + "(SUBJECT TEXT, LECTURER TEXT, DEPARTMENT TEXT, START TEXT, END TEXT, DAY TEXT, YEAR TEXT, SEMESTER TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }

    public void insertJSONData(String Sub, String lec, String day, String start, String end, String dept, String year, String sem){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT, Sub);
        contentValues.put(LECTURER, lec);
        contentValues.put(DAY, day);
        contentValues.put(START, start);
        contentValues.put(END, end);
        contentValues.put(DEPT, dept);
        contentValues.put(YEAR, year);
        contentValues.put(SEM, sem);
        db.insert(TABLE_SCHEDULE, null, contentValues);
    }

    public Cursor DisplayJSONData(String day){
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_SCHEDULE +" WHERE DAY = '" + day + "' AND DEPARTMENT = 'CS' AND YEAR = '2nd' AND SEMESTER = '2nd'";
        Cursor result = db.rawQuery(Query,null);
        return result;
    }
}
