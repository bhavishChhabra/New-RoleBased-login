package com.example.rolebasedlogin;

import static android.content.ContentValues.TAG;

import static com.example.rolebasedlogin.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText uname, pass, conPass, emailsignup;
    public ProgressDialog dialog;
    Button l;
    ImageButton fb,google,number;
    TextView  c,s;
    EditText u, e, p;
    private FirebaseAuth firebaseAuth;
    String currentLanguage = "en", currentLang;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    BeginSignInRequest signInRequest;
    String username = "admin1234";
    String uID = "12345678";
    String emailID = "admin@gmail.com";
    String password = "123456";
    String as = "as";
    private GoogleApiClient googleApiClient;
    String idToken,name,email;
    ImageView imageView;
    private static final int REQ_ONE_TAP = 1 ;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        //SignUp
        getSupportActionBar().hide();
        uname = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        conPass = findViewById(R.id.confirmPassword);
        emailsignup = findViewById(R.id.email);
        //MainActivity
        l = findViewById(id.login);
        s = findViewById(id.signup);
        c = findViewById(id.change);
        e = findViewById(id.email);
        p = findViewById(id.pass);
        fb = findViewById(id.fb);
        number = findViewById(id.number);
        google = findViewById(id.google);
        admin(uID, username, emailID, password, as);
        firebaseAuth = FirebaseAuth.getInstance();
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, SignUp.class);
//                intent.putExtra("email",emailID);
                startActivity(intent);

            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeLang();
            }
        });
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,MainActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,otp.class));

            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterSignin();
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signWithGoogle();
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

    private void signWithGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_ONE_TAP);

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
            Toast.makeText(MainActivity.this, string.already, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser current = firebaseAuth.getCurrentUser();
        if(current!=null){
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
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
                                Toast.makeText(MainActivity.this, string.Success,
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, User.class));

                            } else {
                                Toast.makeText(MainActivity.this, string.Failed, Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_ONE_TAP){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                idToken = account.getIdToken();
                name = account.getDisplayName();
                email = account.getEmail();
                // you can store user data to SharedPreference
                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,
                                            "Login successful", Toast.LENGTH_SHORT).show();
//                                    setContentView(layout.activity_user);
                                    gotoProfile();
                                }else{
                                    Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                                    task.getException().printStackTrace();
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }else{
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Login Unsuccessful. "+result);
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void gotoProfile(){
        Intent intent = new Intent(MainActivity.this, User.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void twitterSignin(){
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        // Target specific email with login hint.
        provider.addCustomParameter("lang",currentLanguage);
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(MainActivity.this,User.class));
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,"This is if " +e.toString() , Toast.LENGTH_LONG).show();
                                }
                            });
        } else {
            firebaseAuth
                    .startActivityForSignInWithProvider(MainActivity.this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(MainActivity.this,User.class));
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                     Toast.makeText(MainActivity.this, "This is else "+e.toString(), Toast.LENGTH_LONG).show();
                 }
            });
        }
    }
}



