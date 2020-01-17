/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeActive = true;

  EditText usernametext;
  EditText passwordedittext;
  TextView changeModeTextView;

  public void showUserList(){

    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(intent);

  }

  public void signUp(View view){


    if(usernametext.getText().toString().matches("") || passwordedittext.getText().toString().matches("")){

      Toast.makeText(this, "A username and password are required!", Toast.LENGTH_LONG).show();

    }else{

      if(signUpModeActive) {

        ParseUser user = new ParseUser();

        user.setUsername(usernametext.getText().toString());
        user.setPassword(passwordedittext.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {

              Log.i("Signup", "Successful!");

              showUserList();
            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {

        ParseUser.logInInBackground(usernametext.getText().toString(), passwordedittext.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if (user!= null){

              Log.i("Login", "Successful!");
              showUserList();
            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

          }
        });
      }
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    usernametext = (EditText) findViewById(R.id.UsernameeditText);
    passwordedittext = (EditText) findViewById(R.id.PasswordeditText);

    changeModeTextView = (TextView) findViewById(R.id.changeModeTextView);
    changeModeTextView.setOnClickListener(this);

    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
    ImageView logo = (ImageView)findViewById(R.id.imageView2);

    relativeLayout.setOnClickListener(this);
    logo.setOnClickListener(this);

    passwordedittext.setOnKeyListener(this);

    if (ParseUser.getCurrentUser()!= null){
      showUserList();
    }
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public void onClick(View view) {

    if(view.getId() == R.id.changeModeTextView){

      Button signUpButton  = (Button) findViewById(R.id.SignUp);
      if (signUpModeActive == true){

        signUpButton.setText("Log In");
        changeModeTextView.setText("Or SignUp");
        signUpModeActive = false;

      }else {

        signUpModeActive = true;
        signUpButton.setText("Sign Up");
        changeModeTextView.setText("Or LogIn");

      }
    } else if (view.getId() == R.id.relativeLayout || view.getId() == R.id.imageView2){

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }
  }

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

      signUp(view);
    }

    return false;
  }
}
