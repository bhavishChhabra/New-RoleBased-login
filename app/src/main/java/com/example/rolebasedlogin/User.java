package com.example.rolebasedlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class User extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    TextView textView;
Button lout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user);
        textView = findViewById(R.id.us);
        lout = findViewById (R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance ();
        lout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish ();
            }
        });
    }
}