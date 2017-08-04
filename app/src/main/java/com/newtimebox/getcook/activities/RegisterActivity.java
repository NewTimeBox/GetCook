package com.newtimebox.getcook.activities;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText etUsername,etPassword;
    Button bRegiser;
    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        General_function.setUpActivity(RegisterActivity.this);

        //ibo commit
        // ImageView backbig = (ImageView) findViewById(R.id.logBackgroudImage);
        initialize();

    }

    private void initialize() {
        ScrollView backbig = (ScrollView) findViewById(R.id.mainScroll);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegiser= (Button) findViewById(R.id.bRegiser);
        TextView tvLogin = (TextView) findViewById(R.id.tvLogin);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backbig.setBackground(new BitmapDrawable(getResources(), General_function.getBitmap(RegisterActivity.this,R.drawable.registerback)));
        }else{
            backbig.setBackgroundResource(R.drawable.registerback);
        }

        mainLayout.setOnClickListener(this);
        bRegiser.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        etPassword.setOnKeyListener(this);

    }

    @Override
    public void onClick(View view) {
        General_function.blurInput(view);
        switch (view.getId()){
            case R.id.tvLogin:
                General_function.LaunchActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }
}
