package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button login = (Button) findViewById(R.id.login_btn);
        TextView signup = (TextView) findViewById(R.id.signup_text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I1 = new Intent(StartActivity.this,MainActivity.class);
                startActivity(I1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I2 = new Intent(StartActivity.this,Register.class);
                startActivity(I2);
            }
        });
    }
}