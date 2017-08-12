package com.newtimebox.getcook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.General_function;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        ImageView imageView = (ImageView)findViewById(R.id.imageView4);
        imageView.startAnimation(animation);
        General_function.setUpActivity(SplashActivity.this);
        Thread timer = new Thread(){
            public void run(){
                try{
                    //3000
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    General_function.LaunchActivity(RegLaunch.class);
                    //burda da ola biler finish();
                    //sadece yeni activity ye kecende uja onsuzda onPause cagirilir
                }
            }
        };
        timer.start();
        //general.Redirect(RegisterActivity.class);


    }

    protected void onPause(){
        super.onPause();
        //General_function.sound_player.release();
        finish();
    }
}
