package com.example.rolebasedlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chaos.view.PinView;
import com.google.firebase.auth.OAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class otp extends AppCompatActivity {
EditText number,otp;
String OTP = "";
String S="";
Button send,submit;
Dialog dialog;
int randomNumber;
PinView pinView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Random random = new Random();
//        pinView=findViewById(R.id.pin_view);
        randomNumber = random.nextInt(999999);
        number = findViewById(R.id.edittext1);
        send = findViewById(R.id.otpbtn);
        dialog= new Dialog(otp.this);
        dialog.setContentView(R.layout.otpsubmit);
        dialog.getWindow().setLayout(650, ViewGroup.LayoutParams.WRAP_CONTENT);
        otp = dialog.findViewById(R.id.edittext2);
        submit = dialog.findViewById(R.id.submit);

//        pinView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                S = s.toString();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number.getText().toString().isEmpty()) {
                    number.setError("Missing field");
                } else {
                    String num = number.getText().toString();
                    String jsonString = "{"+'"'+"company"+'"'+": "+'"'+"Demo_OneNetwork"+'"'+","+'"'+"tenentId"+'"'+": "+'"'+"11"+'"'+","+'"'+"mobile"+'"'+": "+'"'+num+'"'+"}";
                    sendotp(jsonString);
                    dialog.show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(otp.this, "String"+S, Toast.LENGTH_SHORT).show();
                vaildate();
            }
        });
    }
    private void sendotp(String jsonString) {
        AndroidNetworking.initialize(this);
        try {
            AndroidNetworking.post("http://www.trinityapplab.com/DemoOneNetwork/api.php")
                    .addBodyParameter("username","Crest Digital")
                    .addBodyParameter("password","Crest@9999")
                    .addJSONObjectBody(new JSONObject(jsonString))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                OTP = response.getString("otp");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            Toast.makeText(otp.this, "function error" +anError.toString(), Toast.LENGTH_SHORT).show();
//                            textView.setText(anError.toString());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void vaildate(){
        if (otp.getText().toString().equals(OTP)){
            Toast.makeText(otp.this, "otp validate successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(otp.this,User.class));
        }else {
            Toast.makeText(otp.this, "Wrong otp...", Toast.LENGTH_SHORT).show();
        }
     }
}