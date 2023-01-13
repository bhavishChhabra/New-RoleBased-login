package com.example.rolebasedlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;

public class User extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    VideoView videoView;
    private static final long delay = 2000L;
    private boolean mRecentlyBackPressed = false;
    private Handler mExitHandler = new Handler();
    ListView listView;
    CardView cardView;
    TextView textView;
    long backPressedTime = 0;
    ImageView imageView;
    Toolbar toolbar1;
    ArrayList<String> caption1 = new ArrayList<>();
    ArrayList<String> icon1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter0 adapter;
    private List<MyData> data_list;
    public static final String PACKAGE = "com.example.rolebasedlogin";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user);
//        setSupportActionBar(findViewById(R.id.toolbar1));
//        lout = findViewById (R.id.logout);
        listView = findViewById(R.id.lv1);
        textView= findViewById(R.id.text);
//        imageView = findViewById(R.id.logo);
//        computePakageHash();

        android_networking_connection conn = new android_networking_connection(User.this);
        conn.callApi1(listView,textView);

//        recyclerView = (RecyclerView) findViewById(R.id.lv1);
//        data_list  = new ArrayList<>();



}
//    private void load_data_from_server(int id) {
//
//        AndroidNetworking.initialize(User.this);
//        AndroidNetworking.get("http://www.trinityapplab.in/DemoOneNetwork/checklist.php?&empId=9716744965&roleId=10")
//                .setPriority(Priority.HIGH).build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("menu");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject obj = jsonArray.getJSONObject(i);
//                                String avatar = obj.getString("Icon");
//                                String str1 = obj.getString("Caption");
////                                database.addCaption(str1);
////                                caption1 = database.fetchData ();
//                                caption1.add(str1);
//                                icon1.add(avatar);
//                                MyData data = new MyData(i, str1, avatar);
//                                data_list.add(data);
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(User.this, "Catch called: " + e.toString(), Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(User.this, "anError: " + anError.toString(), Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }

                    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Uri video = data.getData();
//                c.setVideoURI(video);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu:
                Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(User.this,MainActivity.class));
                finish ();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void computePakageHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "example.com.linkedinlogindemo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            Log.e("TAG HASH KEY",e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
//        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }
}
