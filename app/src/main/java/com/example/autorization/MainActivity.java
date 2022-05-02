package com.example.autorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etUsername, etPassword;
    Button btnLogin, btnSignup;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);

        etUsername = findViewById(R.id.etUsername);
        etUsername.setOnFocusChangeListener(this::onFocusChanged);
        etPassword = findViewById(R.id.etPassword);
        etPassword.setOnFocusChangeListener(this::onFocusChanged);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();
    }

    public void onFocusChanged(View view, boolean b){
        switch(view.getId()){
            case R.id.etUsername:
                if(b){
                    etUsername.setHint("");
                }
                else{
                    etUsername.setHint("Username");
                }
                break;
            case R.id.etPassword:
                if(b){
                    etPassword.setHint("");
                }
                else{
                    etPassword.setHint("Password");
                }
                break;
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnLogin:
                Cursor loginCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean reg = false;

                if(etUsername.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")){
                    Toast.makeText(this, "Вы вошли", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity3.class));
                }
                else{
                    if(loginCursor.moveToFirst()){
                        int usernameIndex = loginCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                        int passwordIndex = loginCursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                        do{
                            if(etUsername.getText().toString().equals(loginCursor.getString(usernameIndex)) && etPassword.getText().toString().equals(loginCursor.getString(passwordIndex))){
                                startActivity(new Intent(this, MainActivity2.class));
                                reg = true;
                                break;
                            }
                        }while(loginCursor.moveToNext());

                    }
                    loginCursor.close();
                    if(!reg){
                        Toast.makeText(this, "Введены некорректные данные, повторите ввод", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btnSignup:
                Cursor signupCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean founded = false;
                if(etUsername.getText().toString().equals("admin")){
                    Toast.makeText(this, "Вы не можете использовать данный логин \"admin\"", Toast.LENGTH_LONG).show();
                    founded = true;
                }
                else{
                    if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Поля пустые, пожалуйста, введите данные", Toast.LENGTH_LONG).show();
                        founded = true;
                    }
                    else{
                        if (signupCursor.moveToNext()) {
                            int usernameIndex = signupCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                            do {
                                if (etUsername.getText().toString().equals(signupCursor.getString(usernameIndex))) {
                                    Toast.makeText(this, "Данный логин занят", Toast.LENGTH_LONG).show();
                                    founded = true;
                                    break;
                                }
                            } while (signupCursor.moveToNext());
                        }
                    }
                }
                signupCursor.close();
                if(!founded){
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(DBHelper.KEY_LOGIN, etUsername.getText().toString());
                    contentValues.put(DBHelper.KEY_PASSWORD, etPassword.getText().toString());

                    database.insert(DBHelper.TABLE_USERS, null, contentValues);

                    Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}