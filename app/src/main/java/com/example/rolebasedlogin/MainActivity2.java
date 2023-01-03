package com.example.rolebasedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity2 extends AppCompatActivity {
    EditText uname,pass,conPass,emailsignup;
    TextView textView;
    Button sign;

    String emailID = "admin@gmail.com";
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        uname = findViewById(R.id.name);
        pass=findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        conPass = findViewById(R.id.confirmPassword);
        emailsignup=findViewById(R.id.email);
        sign =findViewById(R.id.signupbtn);
        textView =findViewById(R.id.logintv);
//        emailID = intent.getStringExtra("email");
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!conPass.getText().toString().equals(pass.getText().toString())) {
                 conPass.setError("Password is not matching");
                }
                 else if(uname.getText().toString().isEmpty()){
                     uname.setError("Missing field");
                    }
                else{
                    signUp();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });
    }
    private void signUp() {
        if(emailsignup.getText().toString().isEmpty()){
            emailsignup.setError("Missing field");
        }else {
            if (emailsignup.getText().toString().equals(emailID)) {
                emailsignup.setError("Already Taken");
                Toast.makeText(MainActivity2.this, R.string.newEmail, Toast.LENGTH_SHORT).show();
            } else {
             mAuth.createUserWithEmailAndPassword(emailsignup.getText().toString(), conPass.getText().toString())
                        .addOnCompleteListener
                                (this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity2.this, R.string.SuccessSign,
                                                    Toast.LENGTH_SHORT).show();
//                                    DatabaseReference firebaseDatabase;
//                                    firebaseDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Login").child ("users")
//                                            .child (task.getResult ().getUser ().getUid ());
//                                    firebaseDatabase.child ("email").setValue (emailsignup.getText ().toString ());
//                                    firebaseDatabase.child ("Password").setValue (pass.getText ().toString ());
//                                    firebaseDatabase.child ("as").setValue ("user");
                                        } else {
                                            Toast.makeText(MainActivity2.this, R.string.newEmail, Toast.LENGTH_SHORT).show();
                                 }
                             }
                    });
            }
        }
    }
}


