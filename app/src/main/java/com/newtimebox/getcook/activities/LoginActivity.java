package com.newtimebox.getcook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ibo commit
        ImageView backbig = (ImageView) findViewById(R.id.logBackgroudImage);
        backbig.setImageBitmap(General_function.getBitmap(LoginActivity.this,R.drawable.backbig));
    }
}
