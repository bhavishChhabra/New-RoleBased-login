package com.example.rolebasedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

public class MainActivity extends AppCompatActivity {
    Button l, s, c;
    EditText u, e, p;
    Switch active;
    private FirebaseAuth firebaseAuth;

    String username = "admin1234";
    String uID = "12345678";
    String emailID = "admin@gmail.com";
    String password = "123456";
    String as ="as";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        l = findViewById (R.id.login);
        active = findViewById (R.id.switch1);
        s = findViewById (R.id.signup);
        c = findViewById (R.id.change);
        u = findViewById (R.id.user);
        e = findViewById (R.id.email);
        p = findViewById (R.id.pass);
        firebaseAuth = FirebaseAuth.getInstance ();


        admin (uID,username,emailID,password,as);
        s.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                signUp ();
            }
        });
        l.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                login ();
            }
        });

    }

    private void signUp() {
        firebaseAuth.createUserWithEmailAndPassword (e.getText ().toString (), p.getText ().toString ())
                .addOnCompleteListener
                        (this, new OnCompleteListener<AuthResult> () {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful ()) {
                                    Toast.makeText (MainActivity.this, "Success",
                                            Toast.LENGTH_SHORT).show ();
                                    DatabaseReference firebaseDatabase;
                                    firebaseDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Login").child ("users")
                                            .child (task.getResult ().getUser ().getUid ());
                                    firebaseDatabase.child ("Username").setValue (u.getText ().toString ());
                                    firebaseDatabase.child ("Password").setValue (p.getText ().toString ());
                                    firebaseDatabase.child ("as").setValue ("user");

                                } else {
                                    Toast.makeText (MainActivity.this, "Failed", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
    }

    @Override
    protected void onStart() {
        super.onStart ();
        if (prefrences.setDataLogin (MainActivity.this)) {
            if (prefrences.setDataAs (this).equals ("admin")) {
                startActivity (new Intent (MainActivity.this, admin.class));
            } else {

                startActivity (new Intent (MainActivity.this, User.class));
                finish ();
            }

        }
    }
    private void login () {
        firebaseAuth.signInWithEmailAndPassword (e.getText ().toString (), p.getText ().toString ()).addOnCompleteListener (this, new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance ().getReference ();
                databaseReference.child ("login");
                databaseReference.addValueEventListener (new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String s1 = e.getText ().toString ();
                        String s2 = p.getText ().toString ();
                        if (snapshot.child (s1).exists ()) {
                            if (snapshot.child (s1).child ("password").getValue (String.class).equals (s2)) {
                                if (active.isChecked ()) {
                                    if (snapshot.child ("as").getValue (String.class).equals ("admin")) {
                                        prefrences.setDataAs (MainActivity.this, "admins");
                                        prefrences.setDataLogin (MainActivity.this, true);
                                        startActivity (new Intent (MainActivity.this, admin.class));

                                    } else if (snapshot.child ("as").getValue (String.class).equals ("user")) {
                                        prefrences.setDataAs (MainActivity.this, "users");
                                        prefrences.setDataLogin (MainActivity.this, true);
                                        startActivity (new Intent (MainActivity.this, User.class));

                                    }
                                }
                            } else {
                                if (snapshot.child ("as").getValue (String.class).equals ("admin")) {
                                    prefrences.setDataLogin (MainActivity.this, false);
                                } else if (snapshot.child ("as").getValue (String.class).equals ("user")) {
                                    prefrences.setDataLogin (MainActivity.this, false);
                                }
                                Toast.makeText (MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show ();
                            }
                        } else {
                            Toast.makeText (MainActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show ();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        });
    }
    public void admin (String userID, String name, String email, String password,String type){
        admin admin = new admin (name, email, password,type);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance ().getReference ().child ("Login").child ("admins").child (userID);
        mDatabase.child ("username").setValue (name);
        mDatabase.child ("password").setValue (password);
        mDatabase.child ("email ID").setValue (email);
        mDatabase.child ("as").setValue ("admin");


    }
}



