package com.newtimebox.getcook.activities;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    EditText etUsername,etPassword;
    Button bLogin;
    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       General_function.setUpActivity(LoginActivity.this);
        General_function.LaunchActivity(FirebaseAuthTest.class);
        //ibo commit
       // ImageView backbig = (ImageView) findViewById(R.id.logBackgroudImage);
        initialize();

    }

    private void initialize() {
        ScrollView backbig = (ScrollView) findViewById(R.id.mainScroll);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        TextView tvReg = (TextView) findViewById(R.id.tvReg);

        General_function.setStaticContext(LoginActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backbig.setBackground(new BitmapDrawable(getResources(), General_function.getBitmap(LoginActivity.this,R.drawable.registerback)));
        }else{
            backbig.setBackgroundResource(R.drawable.registerback);
        }

        mainLayout.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        tvReg.setOnClickListener(this);
        etPassword.setOnKeyListener(this);

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
            login();
        }

        return false;
    }

    @Override
    public void onClick(View view) {

//        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if((view instanceof EditText)==false){
//            View currentFocus = (EditText)  getCurrentFocus();
//           im.hideSoftInputFromWindow(currentFocus.getWindowToken(),0);
//          //  etUsername.setEnabled(false);
//            //currentFocus.clearFocus();
//
//            //getCurrentFocus().clearFocus();
//        }
        General_function.blurInput(view,LoginActivity.this);

        switch (view.getId()){
            case R.id.bLogin:
                login();
                break;
            case R.id.mainLayout:

                break;
            case R.id.tvReg:
                General_function.LaunchActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        Toast.makeText(LoginActivity.this,"Login clicked",Toast.LENGTH_LONG).show();
    }


}
