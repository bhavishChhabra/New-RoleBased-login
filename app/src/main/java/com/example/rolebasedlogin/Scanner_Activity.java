package com.example.rolebasedlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Scanner_Activity extends AppCompatActivity {
    Context context;
    ScanOptions scanOptions = new ScanOptions();
    ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result -> {
//        if (result.getContents() != null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Result");
//            builder.setCancelable(false);
//            builder.setMessage(result.getContents().toString());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).show();
//        } else {
//
//        }
    });

    public Scanner_Activity(Context context) {
        this.context = context;
    }

    Button button;

    @SuppressLint("MissingInflatedId")

    public void New() {
        Toast.makeText(context, "Method Called", Toast.LENGTH_LONG).show();
    }

    public void scanCode() {

        scanOptions.setPrompt("Volume up to flash on");
//        scanOptions.setBeepEnabled(true);
//        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(Capture.class);
        Toast.makeText(context, "Method Called", Toast.LENGTH_LONG).show();
//        launcher.launch(scanOptions);


    }
}
