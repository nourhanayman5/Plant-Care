package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        EditText res_pass=(EditText) findViewById(R.id.editTextText3);
        EditText con_pass=(EditText) findViewById(R.id.editTextText4);
        Button con_btn=(Button) findViewById(R.id.button4);
        TextView username=(TextView) findViewById(R.id.textView7);
        DatabaseClass DB = new DatabaseClass(this);

        Intent i=getIntent();
        username.setText(i.getStringExtra("username"));

        con_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = res_pass.getText().toString();
                String repass = con_pass.getText().toString();
                if (pass.equals(repass)) {
                    Boolean checkpassupdate = DB.UpdatePassword(user, pass);
                    if (checkpassupdate == true) {
                        open_login_reset();
                        Toast.makeText(ResetPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetPassword.this, "Password NOT Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ResetPassword.this, "Passwords NOT Matched", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    void  open_login_reset(){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}
