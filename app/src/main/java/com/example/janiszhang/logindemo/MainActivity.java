package com.example.janiszhang.logindemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.janiszhang.logindemo.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mSQliteDatabase;
    private EditText name;
    private EditText password;
    private Button loginButton;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mSQliteDatabase = databaseHelper.getWritableDatabase();

        name = (EditText) findViewById(R.id.et_name);
        password = (EditText) findViewById(R.id.et_password);
        loginButton = (Button) findViewById(R.id.bt_login);

        sharedpreferences = getSharedPreferences("sharedpreferences_name", Context.MODE_PRIVATE);
        String lastLogin = sharedpreferences.getString("lastLogin", "");
        if(!TextUtils.isEmpty(lastLogin)) {
            name.setText(lastLogin);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String passwordText = password.getText().toString();

                Cursor query = mSQliteDatabase.query(DatabaseHelper.USER_TABLE_NAME, new String[]{DatabaseHelper.PASSWORD, DatabaseHelper.LOGINSTATE}, DatabaseHelper.USERNAME + "=?", new String[] {nameText}, null, null, null);

                if(query.moveToFirst()) {
                       String userPassword = query.getString(query.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));
                       String userLoginState = query.getString(query.getColumnIndexOrThrow(DatabaseHelper.LOGINSTATE));
                    if(TextUtils.equals(passwordText,userPassword)) {
                        login(nameText);
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        intent.putExtra("username", nameText);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this,"密码不正确",Toast.LENGTH_LONG).show();
                    }
                } else {
                    insert(nameText, passwordText);
                }

            }
        });

    }

    public  void login(String nameText) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LOGINSTATE, "1");
        mSQliteDatabase.update(DatabaseHelper.USER_TABLE_NAME, contentValues,"username=?",new String[]{nameText});
    }


    public void insert(String nameText, String passwordText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERNAME,nameText);
        contentValues.put(DatabaseHelper.PASSWORD,passwordText);
        contentValues.put(DatabaseHelper.LOGINSTATE,"1");
        mSQliteDatabase.insert(DatabaseHelper.USER_TABLE_NAME, null, contentValues);
    }


}
