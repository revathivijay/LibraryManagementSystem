package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    TextView forget_psw;
    Button btnsubmit;
    String Name, Email, Login, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusername = (EditText) findViewById(R.id.editText);
        txtpassword = (EditText) findViewById(R.id.editText2);
        btnsubmit = (Button) findViewById(R.id.button2);
        forget_psw =(TextView) findViewById(R.id.forget_pw);

        forget_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,Register.class);
                startActivity(i);

            }
        });
    }
}
