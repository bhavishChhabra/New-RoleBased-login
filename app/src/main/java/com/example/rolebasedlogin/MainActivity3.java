package com.example.rolebasedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ArrayList<String> arr1 = new ArrayList<>();
    ArrayList<String> caption2 = new ArrayList<>();
    ArrayList<String> icon2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        textView = findViewById(R.id.tv3);
        listView = findViewById(R.id.lv3);
        Intent i = getIntent();
        arr1 = i.getStringArrayListExtra("jsonarray");
        try {
            JSONArray jsonArray = new JSONArray(arr1.get(0));
            for (int j=0;j< jsonArray.length();j++){
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String caption = jsonObject.getString("Caption");
                String icon = jsonObject.getString("Icon");
                caption2.add(caption);
                icon2.add(icon);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.textviewlayout,R.id.tv1,caption2);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            ArrayList<String> act2 = new ArrayList<>();
                            ArrayList<String> act3 = new ArrayList<>();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("subCategoryList");
                            if (jsonArray1.length()==0){
                                String chkpid = jsonObject.getString("checkpointId");
                                String[] strarr = chkpid.replace(":",",").split(",");
                                Log.d("checkpointid", "onItemClick: "+chkpid);
                                for (int k=0;k<strarr.length;k++){
                                    act2.add(strarr[k]);
                                }
//                                Toast.makeText(MainActivity3.this, "chkpid: "+chkpid, Toast.LENGTH_SHORT).show();
//
//                                Toast.makeText(MainActivity3.this, "act2: "+act2.size(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity3.this,MainActivity4.class);
                                intent.putStringArrayListExtra("chkpid1",act2);
                                startActivity(intent);
                            }else {
                                act3.add(jsonArray1.toString());
                                Intent intent = new Intent(MainActivity3.this,MainActivity5.class);
                                intent.putStringArrayListExtra("jsonarray",act3);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(MainActivity3.this, User.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}