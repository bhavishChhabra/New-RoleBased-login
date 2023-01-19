package com.example.rolebasedlogin;

import static android.graphics.Color.TRANSPARENT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class Camera_Gallery extends AppCompatActivity {
    public Camera_Gallery(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
    }

    ImageView imageView;
Context context;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
@SuppressWarnings("deprecation")
public void capture(){
    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);


    }
    Toast.makeText(context, "clicked!!", Toast.LENGTH_SHORT).show();
}
public void open(){

    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Camera_Gallery.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , 1);
        }
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("Data");
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_account_box_24));
                   Uri uri = data.getData();
                   imageView.setImageURI(uri);
                    Toast.makeText(this, "RequestCade" + requestCode, Toast.LENGTH_SHORT)
                            .show();
                    imageView.setBackground(new ColorDrawable(TRANSPARENT));
            }
            if (requestCode == 1) {
                    Uri selectedImage = data.getData();
                    Toast.makeText(this, "RequestCade" + requestCode, Toast.LENGTH_SHORT).show();
                    imageView.setImageURI(selectedImage);
                    imageView.setBackground(new ColorDrawable(TRANSPARENT));
            }
        }
    }
}