package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        DatabaseClass DB = new DatabaseClass(this);

        Button reset_btn = (Button) findViewById(R.id.button3);
        Cursor user = DB.User_found();
        EditText username = (EditText) findViewById(R.id.editTextText7);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = false;
                while (!user.isAfterLast()) {
                    if (user.getString(0).equals(username.getText().toString())) {
                        Intent intent = new Intent(ForgetPassword.this, ResetPassword.class);
                        intent.putExtra("username", username.getText().toString());
                        startActivity(intent);
                        Toast.makeText(ForgetPassword.this, "User Found Successfully", Toast.LENGTH_SHORT).show();
                        check = true;
                        break;
                    }
                    user.moveToNext();
                }
                if (!check)
                    Toast.makeText(ForgetPassword.this, "This user not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
