package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Treatment extends AppCompatActivity {

    TextView treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        treatment = (TextView) findViewById(R.id.treatment);
        String diseaseTreatment = getIntent().getStringExtra("treatment");
        treatment.setText(diseaseTreatment);
    }
}