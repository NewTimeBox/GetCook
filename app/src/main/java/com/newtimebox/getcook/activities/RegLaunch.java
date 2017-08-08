package com.newtimebox.getcook.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.newtimebox.getcook.R;
import com.newtimebox.getcook.helpers.FullRequest;
import com.newtimebox.getcook.helpers.General_function;
import com.newtimebox.getcook.interfaces.ICallback;

public class RegLaunch extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    /*FireBase variables down*/

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static int RC_SIGN_IN = 0;

    /*FireBase variables up*/
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_launch);
        General_function.setUpActivity(RegLaunch.this);
        initialize();
    }



    private void initialize() {
        setUpFirebase();
        findViewById(R.id.bLogin).setOnClickListener(this);
    }


    private void singOut() {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //do what you want
                    }
                });
    }

    private void signIn() {
        pd = new ProgressDialog(General_function.StaticCurrentContext);
        pd.setMessage("loading");
        pd.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }



    private void setUpFirebase() {
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
                    FullRequest requester = new FullRequest();
                    requester.setContext(General_function.StaticCurrentContext);
                    requester.send_request(FullRequest.primaryDomain,new String[]{

                            "email:"+user.getEmail(),
                            "fullname:"+user.getDisplayName(),
                            "firebaseId:"+ user.getUid(),
                    },"POST",new ICallback(){
                        @Override
                        public void call(String ...params){
                            Toast.makeText(General_function.StaticCurrentContext,params[0]+"",Toast.LENGTH_LONG).show();
                            pd.hide();
                        }
                    });
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
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bLogin:
                signIn();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
