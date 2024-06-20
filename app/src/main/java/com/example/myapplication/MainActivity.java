package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String user;
    ImageView arrow;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        User userEmail = (User) getApplicationContext();
        EditText email=(EditText) findViewById(R.id.Email);
        EditText password=(EditText) findViewById(R.id.Password);
        ImageView login =(ImageView) findViewById(R.id.Login);
        register = (TextView) findViewById(R.id.textView5);
        arrow = (ImageView) findViewById(R.id.logarrow);
//        Button register =(Button) findViewById(R.id.Register);
        CheckBox checkBox =(CheckBox) findViewById(R.id.checkBox) ;

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent arrow = new Intent(MainActivity.this,Api.class);
                startActivity(arrow);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_signup();
            }
        });

        SharedPreferences sp=getSharedPreferences("Data" , MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        DatabaseClass Add_User =  new DatabaseClass(this);
        boolean login1=sp.getBoolean("ISLOGGEDIN" , false);
        if (login1==true){
            startActivity(new Intent(MainActivity.this, Home.class));
//            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor user = Add_User.User_found();
                boolean check = false;
                while (!user.isAfterLast()) {
                    if (user.getString(1).equals(email.getText().toString()) &&
                            user.getString(2).equals(password.getText().toString())) {

                        if (checkBox.isChecked()) {
                            editor.putString("email", email.getText().toString());
                            editor.putString("password", password.getText().toString());
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Login Successfully1", Toast.LENGTH_SHORT).show();
                        }

                        userEmail.setEmail(email.getText().toString());
                        userEmail.setUsername(user.getString(0));
                        Intent intent;
                        intent = new Intent(MainActivity.this , Home.class);
//                        intent.putExtra("username", email.getText().toString());
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        check = true;
                        break;
                    }
                    user.moveToNext();
                }
                if (!check)
                    Toast.makeText(MainActivity.this, "This user not Found", Toast.LENGTH_SHORT).show();
            }
        });
        TextView forget_pass=(TextView) findViewById(R.id.textView9);
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_Forgetpassword();
            }
        });
    }
    void open_signup()
    {
        Intent intent = new Intent(this ,Register.class);
        startActivity(intent);
    }

    void open_Forgetpassword()
    {
        Intent intent = new Intent(this ,ForgetPassword.class);
        startActivity(intent);
    }
}