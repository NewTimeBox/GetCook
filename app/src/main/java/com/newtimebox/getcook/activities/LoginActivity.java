package com.newtimebox.getcook.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

        //ibo commit
    }

    private void initialize() {
        ConstraintLayout mainlayout = (ConstraintLayout) findViewById(R.id.mainlayout);




        Bitmap bmp = General_function.getBitmap(LoginActivity.this,R.drawable.login_background);
        Drawable d = new BitmapDrawable(getResources(), bmp);
        mainlayout.setBackground(d);
    }
}

