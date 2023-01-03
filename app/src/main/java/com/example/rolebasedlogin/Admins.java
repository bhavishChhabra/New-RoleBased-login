package com.example.rolebasedlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Admins extends AppCompatActivity {
TextView textView;
    private FirebaseAuth firebaseAuth;
    Button lout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admins);
        textView = findViewById(R.id.ad);
        lout = findViewById (R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance ();
        lout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(Admins.this,MainActivity.class));
                finish ();
            }
        });
    }
}