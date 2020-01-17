package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    TextView register_btn;
    Button btnsubmit;
    Context context;

    public void testLogin(View view){
        String sname = txtusername.getText().toString();
        String pass = txtpassword.getText().toString();

        Background background = new Background(this);
        background.execute(sname, pass);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        txtusername = (EditText) findViewById(R.id.editText);
        txtpassword = (EditText) findViewById(R.id.editText2);
        btnsubmit = (Button) findViewById(R.id.button2);
        register_btn =(TextView) findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,Register.class);
                startActivity(i);
            }
        });


    }

}
