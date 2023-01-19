package com.example.rolebasedlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();
//        Window window = activity.getWindow();
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active on device
                // For WHITE status bar Icons color to dark
                getWindow().setStatusBarColor(ContextCompat.getColor(SplashScreenActivity.this,R.color.white));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active on device
                //// clear FLAG_TRANSLUCENT_STATUS flag:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
                getWindow().setStatusBarColor(ContextCompat.getColor(SplashScreenActivity.this,R.color.black));
                break;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(SplashScreenActivity.this,User.class));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                }
                // on the below line we are finishing
                // our current activity.
                finish();
            }
        }, 1000);


    }
}