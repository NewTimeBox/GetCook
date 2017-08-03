package com.newtimebox.getcook.activities;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class LoginActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ibo commit
       // ImageView backbig = (ImageView) findViewById(R.id.logBackgroudImage);
        ScrollView backbig = (ScrollView) findViewById(R.id.mainScroll);
        backbig.setBackground(new BitmapDrawable(getResources(), General_function.getBitmap(LoginActivity.this,R.drawable.registerback)));
    }
}
