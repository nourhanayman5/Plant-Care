package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity {

    User user_name;
    ImageView arrow;
    TextView register ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        arrow = (ImageView) findViewById(R.id.regarrow);
        user_name = (User) getApplicationContext();
        TextView btn_Login = (TextView) findViewById(R.id.Login);
        Button btn_Register = (Button)findViewById(R.id.SignUp);
        DatabaseClass Add_User=new DatabaseClass(getApplicationContext());
        EditText clenedar = (EditText) findViewById(R.id.birth) ;
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent arrow = new Intent(Register.this,StartActivity.class);
                startActivity(arrow);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Login();
            }
        });
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Name = (EditText) findViewById(R.id.Name);
                EditText Email = (EditText)findViewById(R.id.Email);
                EditText PassWord = (EditText)findViewById(R.id.Password);
                Cursor user = Add_User.User_found();
                boolean check = true ;
                while(!user.isAfterLast())
                {
                    if(user.getString(1).equals( Email.getText().toString())) {
                        Toast.makeText(Register.this, "sorry change email ", Toast.LENGTH_SHORT).show();
                        check = false ;
                        break;
                    }
                    user.moveToNext();
                }
                if(check) {
                    Add_User.insertUser(Name.getText().toString(), Email.getText().toString(), PassWord.getText().toString(),clenedar.getText().toString());
                    Toast.makeText(Register.this, "User " + Name.getText().toString() + "Register success", Toast.LENGTH_SHORT).show();

                    open_Login();
                }
                // Add_User.insertUser(Name.getText().toString(), Email.getText().toString(), PassWord.getText().toString(),clenedar.getText().toString());
            }
        });

        Calendar calnder=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calnder.set(Calendar.YEAR,year);
                calnder.set(Calendar.MONTH,month);
                calnder.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                updateCalender();
            }
            private void  updateCalender()
            {
                String format ="MM/dd/yy";
                SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
                clenedar.setText(sdf.format(calnder.getTime()));
            }
        };
        clenedar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Register.this,date,calnder.get(Calendar.YEAR),calnder.get(Calendar.MONTH),
                        calnder.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    void open_Login()
    {
        Intent intent = new Intent(this ,MainActivity.class);
        startActivity(intent);
    }
}

