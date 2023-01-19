package com.example.rolebasedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    TextView textView;
    ListView listView;
    Toolbar toolbar;
    ImageView imageView;
    ImageButton imageButton;
    ArrayList<String> arr1 = new ArrayList<>();
    ArrayList<String> caption2 = new ArrayList<>();
    ArrayList<String> icon2 = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imageButton= findViewById(R.id.signout);
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this, User.class));
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this, User.class));
            }
        });
//        textView = findViewById(R.id.tv3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        listView = findViewById(R.id.lv1);
        Intent i = getIntent();
        // assigning ID of the toolbar to a variable
//        toolbar = findViewById(R.id.toolbar1);

        // using toolbar as ActionBar
//        setSupportActionBar(toolbar);
        arr1 = i.getStringArrayListExtra("jsonarray");
        try {
            JSONArray jsonArray = new JSONArray(arr1.get(0));
            for (int j=0;j< jsonArray.length();j++){
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String caption = jsonObject.getString("Caption");
                String icon = jsonObject.getString("Icon");
                caption2.add(caption);
                icon2.add(icon);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//                });
            }

            CustomAdapter1 customAdapte1 = new CustomAdapter1(MainActivity3.this,caption2,icon2,arr1);
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.textviewlayout,R.id.tv1,caption2);
            listView.setAdapter(customAdapte1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}