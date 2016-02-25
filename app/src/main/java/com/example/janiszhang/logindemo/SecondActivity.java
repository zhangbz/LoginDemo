package com.example.janiszhang.logindemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.janiszhang.logindemo.database.DatabaseHelper;

/**
 * Created by janiszhang on 2016/2/26.
 */
public class SecondActivity extends Activity{

    private SQLiteDatabase mSQliteDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second_activity);

        final String username = getIntent().getStringExtra("username");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mSQliteDatabase = databaseHelper.getWritableDatabase();

        Button logoutButton = (Button) findViewById(R.id.bt_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(username);
                SharedPreferences lastLogin = getSharedPreferences("sharedpreferences_name", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = lastLogin.edit();
                edit.putString("lastLogin", username);
                edit.apply();
                startActivity(new Intent(SecondActivity.this,MainActivity.class));
            }
        });
    }

    public  void logout(String nameText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LOGINSTATE,"0");
        mSQliteDatabase.update(DatabaseHelper.USER_TABLE_NAME, contentValues, "username=?", new String[]{nameText});
    }
}
