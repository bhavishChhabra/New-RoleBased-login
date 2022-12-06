package com.example.rolebasedlogin;

import static android.graphics.Color.RED;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button l, s, c;
    EditText u, e, p;
    private FirebaseAuth firebaseAuth;
    String currentLanguage = "en", currentLang;

    String username = "admin1234";
    String uID = "12345678";
    String emailID = "admin@gmail.com";
    String password = "123456";
    String as = "as";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = findViewById(R.id.login);
        s = findViewById(R.id.signup);
        c = findViewById(R.id.change);
        e = findViewById(R.id.email);
        p = findViewById(R.id.pass);
        firebaseAuth = FirebaseAuth.getInstance();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeLang();
            }
        });


    admin(uID, username, emailID, password, as);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.getText().toString().isEmpty()||p.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Missing field", Toast.LENGTH_SHORT).show();
                }else{
                    signUp();

                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.getText().toString().isEmpty()||p.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Missing field", Toast.LENGTH_SHORT).show();
                }else{
                    login();

                }
            }
        });

    }

    public void ChangeLang() {
        final String lang[] = {"English", "हिन्दी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Change Language");
        mBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setlocale("");
                    recreate();
                }
                else if (i == 1) {
                    setlocale("hi");
                    recreate();
                }
            }
        });

        mBuilder.create();
        mBuilder.show();
    }

    private void setlocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            Locale myLocale;
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(MainActivity.this, R.string.already, Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp() {
        if(e.getText().toString().equals(emailID)){
            Toast.makeText(MainActivity.this, R.string.newEmail, Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(e.getText().toString(), p.getText().toString())
                    .addOnCompleteListener
                            (this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, R.string.SuccessSign,
                                                Toast.LENGTH_SHORT).show();
                                    DatabaseReference firebaseDatabase;
                                    firebaseDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Login").child ("users")
                                            .child (task.getResult ().getUser ().getUid ());
                                    firebaseDatabase.child ("email").setValue (e.getText ().toString ());
                                    firebaseDatabase.child ("Password").setValue (p.getText ().toString ());
                                    firebaseDatabase.child ("as").setValue ("user");

                                    } else {
                                        Toast.makeText(MainActivity.this, R.string.newEmail, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser current = firebaseAuth.getCurrentUser();
        if(current!=null){
            startActivity(new Intent(MainActivity.this, User.class));

        }
    }

    private void login() {
        if (e.getText().toString().equals(emailID) && p.getText().toString().equals(password)) {
            startActivity(new Intent(MainActivity.this, Admins.class));
        } else {
            firebaseAuth.signInWithEmailAndPassword(e.getText().toString(), p.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, R.string.Success,
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, User.class));

                            } else {
                                Toast.makeText(MainActivity.this, R.string.Failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void admin(String userID, String name, String email, String password, String type) {
        admin admin = new admin(name, email, password, type);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Login").child("admins").child(userID);
        mDatabase.child("username").setValue(name);
        mDatabase.child("password").setValue(password);
        mDatabase.child("email ID").setValue(email);
        mDatabase.child("as").setValue("admin");

    }
}



