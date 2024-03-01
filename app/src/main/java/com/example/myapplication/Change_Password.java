package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Change_Password extends AppCompatActivity {
    User u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        u_name = (User) getApplicationContext();
        EditText res_pass=(EditText) findViewById(R.id.reset_pass);
        EditText con_pass= (EditText) findViewById(R.id.confirm_pass);
        Button con_btn=(Button) findViewById(R.id.change_btn);
//        TextView username=(TextView) findViewById(R.id.textView7);
        DatabaseClass DB = new DatabaseClass(this);

        con_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = u_name.getUserName();
                String pass = res_pass.getText().toString();
                String repass = con_pass.getText().toString();
                if (pass.equals(repass)) {
                    Boolean checkpassupdate = DB.UpdatePassword(user, pass);
                    if (checkpassupdate == true) {
                        open_login_reset();
                        Toast.makeText(Change_Password.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Change_Password.this, "Password NOT Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Change_Password.this, "Passwords NOT Matched", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    void  open_login_reset(){
        Intent intent = new Intent(this , Profile.class);
        startActivity(intent);
    }

    }
