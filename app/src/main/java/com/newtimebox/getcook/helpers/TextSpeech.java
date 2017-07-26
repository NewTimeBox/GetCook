package com.newtimebox.getcook.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by User on 30.06.2017.
 */

public class TextSpeech implements TextToSpeech.OnInitListener{
    Context context;
    TextToSpeech ts;
    boolean isReady = false;
    static TextSpeech tx;


    public TextSpeech(Context context){
        this.context = context;
        _init();
    }

    private void _init() {
        ts = new TextToSpeech(context, this);
    }

    public void speak(final String text, final int queue_mod){
        if(isReady){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ts.speak(text,queue_mod,null,null);
            } else {
                ts.speak(text,queue_mod,null);
            }

        }else{
            final TextSpeech that = this;
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    that.speak(text,queue_mod);
                }
            });
        }

    }
    public void speak(String text){
        this.speak(text,TextToSpeech.QUEUE_FLUSH);
    }

    public void stopSpeech(){
        ts.stop();
        ts.shutdown();
    }

    public static void general_speak(Context context,String text,int queue_mod){
        tx = new TextSpeech(context);
        tx.speak(text,queue_mod);

    }
    public static void general_speak(Context context,String text){
        general_speak(context,text,TextToSpeech.QUEUE_FLUSH);

    }


    @Override
    public void onInit(int status) {
        if(status!=TextToSpeech.ERROR){
            ts.setLanguage(Locale.US);
            isReady=true;
        }
    }
}
