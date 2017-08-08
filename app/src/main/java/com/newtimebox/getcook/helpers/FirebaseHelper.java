package com.newtimebox.getcook.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by User on 08.08.2017.
 */

public class FirebaseHelper implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static int RC_SIGN_IN = 0;


    Context currentContext;
    static Context currentStaticContext;

    public void setContext(Context context){
        currentContext=context;
    }

    public void setStaticContext(Context context){
        currentStaticContext=context;
    }


    public void  setUpFirebase(){
        mAuth = FirebaseAuth.getInstance();//mAuth.signOut();
        //auth fail olarsa mAuth.signOut(); sil sonra yerne qoy
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //user.unlink(user.getProviderId()); FirebaseAuth.getInstance().signOut();
                    Log.d("Firebase_test", "Email:"+user.getEmail()+
                            " | Name"+user.getDisplayName()+" | PhoneNumber"+user.getPhoneNumber()+"onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Firebase_test", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("599216467394-vsmfuelmvq5l8jt1vhv2acf4lvpprd1f.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(currentContext)
                .enableAutoManage((FragmentActivity) currentContext,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //onstart methodlari falan qalib
}
