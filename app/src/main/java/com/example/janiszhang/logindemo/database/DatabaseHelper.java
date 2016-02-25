package com.example.janiszhang.logindemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by janiszhang on 2016/2/25.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "login";
    public static final int VERSION = 1;
    public  static final String USER_TABLE_NAME = "user";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGINSTATE = "loginstate";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + "(" + USERNAME + "varchar(20) not null,"+PASSWORD+" varchar(20) not null, " + LOGINSTATE + " varchar(20) not null");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
