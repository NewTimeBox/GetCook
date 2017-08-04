package com.newtimebox.getcook.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.newtimebox.getcook.R;

/**
 * Created by User on 29.01.2017.
 */



public class General_function {

    public Context CurrentContext;
    public static Context StaticCurrentContext;
    public static MediaPlayer sound_player;
    public static int sound_player_src;
    public General_function(Context location){ this.CurrentContext = location;}
    private Bundle extras;
//https://android-developers.googleblog.com/2015/05/android-design-support-library.html

    public void Redirect(Class<?> target,Context location){
        //https://www.youtube.com/user/mybringback/playlists
        this.CurrentContext = location;
        this.Redirect(target);
    }
    public void Redirect(Class<?> target,Context location,Bundle extras){
        this.extras = extras;
        this.CurrentContext = location;
        this.Redirect(target);
    }


    public void Redirect(Class<?> target) {
        //animation ferqlendirmek ucun asagidaki linklere baxa bilersen
        //https://stackoverflow.com/questions/3515264/can-i-change-the-android-startactivity-transition-animation
        //https://stackoverflow.com/questions/4940574/how-to-override-the-enter-activity-animation-if-it-is-stated-by-launcher/4940610#4940610


        Intent new_Activity_page = new Intent(this.CurrentContext,target);
        if(this.extras != null){
            new_Activity_page.putExtras(this.extras);
        }

        this.CurrentContext.startActivity(new_Activity_page);
    }
    public static void LaunchActivity(Class<?> target) {

        LaunchActivity(target,null);
    }
    public static void LaunchActivity(Class<?> target,Bundle extras) {
        //Redirect ile eyni seydi sadece staticdi

        Intent new_Activity_page = new Intent(StaticCurrentContext,target);
        if(extras != null){
            new_Activity_page.putExtras(extras);
        }

        StaticCurrentContext.startActivity(new_Activity_page);
    }



    public  void setContext(Context context){
        this.CurrentContext = context;
    }


    public General_function sleep_time(int milli){
        Thread times = new Thread();
        try {
            times.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }


    public static MediaPlayer SoundPlayer(Context ctx,int raw_id,int isLoop){
        sound_player = MediaPlayer.create(ctx, raw_id);
        sound_player_src=raw_id;
        if(isLoop==1){
            sound_player.setLooping(true);
        }else{
            sound_player.setLooping(false);
        }
        // Set looping
        //sound_player.setVolume(100, 100);

        //player.release();
        sound_player.start();
        return sound_player;
    }
    public static MediaPlayer SoundPlayer(Context ctx){
        return SoundPlayer(ctx,sound_player_src);
    }
    public static MediaPlayer SoundPlayer(Context ctx,int raw_id){
        return SoundPlayer(ctx,raw_id,0);
    }

    public static void reduceFPS(float x){

        try {
            Thread.sleep((long) (1000/x));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static long time(){
        return System.currentTimeMillis()/1000;
    }

    public static long time(boolean milli){
        long timestamp = System.currentTimeMillis();
        if(milli==false){
            timestamp=timestamp/1000;
        }
        return timestamp;
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap ResizeBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        //getResizedBitmap ile eyni
        //k
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap getBitmap(Context context,int ResourceId){
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=1;
        bitmapOptions.inTargetDensity=1;
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),ResourceId,bitmapOptions);
        return bmp;
    }


    public static void hideSystemUI(View mDecorView) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    public static void showSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }



    /*
    * Asagi Soundpool sound dur
    * */

    SoundPool soundpool=null;
    //int[] loaded_sound_pools = {};
    int last_loaded_soundId=0;
    int last_loaded_soundResource=0;
    protected  SoundPool createSoundPool() {
        if(soundpool!=null){
            return soundpool;

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createNewSoundPool();
        } else {
            return createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected SoundPool createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundpool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        return soundpool;
    }

    @SuppressWarnings("deprecation")
    protected SoundPool createOldSoundPool(){
        soundpool = new SoundPool(15, AudioManager.STREAM_MUSIC,0);
        return soundpool;
    }

    public SoundPool SoundLittle(final int soundResourceId){
        createSoundPool();
        // if(!Arrays.asList(loaded_sound_pools).contains(soundResourceId)){
        if(last_loaded_soundResource!=soundResourceId){
            final int soundId = soundpool.load(CurrentContext, soundResourceId, 1);
            soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    // in 2nd param u have to pass your desire ringtone
                    // loaded_sound_pools[loaded_sound_pools.length]=soundResourceId;
                    last_loaded_soundResource=soundResourceId;
                    last_loaded_soundId=soundId;
                    soundpool.play(soundId, 1, 1, 0, 0, 1);
                }
            });
        }else{
            //eger evvelden var ise
            soundpool.play(last_loaded_soundId, 1, 1, 0, 0, 1);
        }

        return soundpool;
    }



    public static boolean isInternetConnected(Context context) {
        StaticCurrentContext=context;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isInternetConnected() {
        return isInternetConnected(StaticCurrentContext);
    }

    /*
    * Yuxari Soundpool sound dur
    * */


}
