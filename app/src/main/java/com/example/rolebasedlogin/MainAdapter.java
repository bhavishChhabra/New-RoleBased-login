package com.example.rolebasedlogin;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    //    int v;
    ArrayList<String> checkpointid = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> typeId = new ArrayList<>();
    ArrayList<String[]> value = new ArrayList<>();
    ArrayList<String> size = new ArrayList<>();
    ArrayList<String> editable = new ArrayList<>();
    ArrayList<String> score = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher ;
    Activity activity;
    ImageView imageView;
    //    String size;
    //    int p;
    RecyclerView.ViewHolder h;
    //    ArrayList<String> pos = new ArrayList<>();

    public MainAdapter(Context context, ArrayList<String> checkpointid, ArrayList<String> description, ArrayList<String> typeId, ArrayList<String[]> value, ArrayList<String> size, ArrayList<String> editable, ArrayList<String> score) {
        this.context = context;
        this.checkpointid = checkpointid;
        this.description = description;
        this.typeId = typeId;
        this.value = value;
        this.size = size;
        this.editable = editable;
        this.score=score;
    }

    public ArrayList<String> getCheckpointid() {
        return checkpointid;
    }
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility", "ResourceAsColor"})
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        vt.add(String.valueOf(viewType));
//        v=viewType;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (typeId.get(viewType).equals("1")) {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("2")) {
            View view = layoutInflater.inflate(R.layout.address_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("3")) {
            View view = layoutInflater.inflate(R.layout.contact_no_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("4")) {
            View view = layoutInflater.inflate(R.layout.checkbox_layout, parent, false);
            View view1 = layoutInflater.inflate(R.layout.radiobtn_layout, parent, false);
            String[] v = null;
            v = value.get(viewType);
            RadioGroup radioGroup = view1.findViewById(R.id.radiogroup);
            String s = "1";

            if (size.get(viewType).equals("0")) {
                LinearLayout linearLayout = view.findViewById(R.id.lout);
                GridLayout gridLayout = view.findViewById(R.id.checkbox_grid);
                for (int i = 0; i < v.length; i++) {
                    CheckBox checkBox = new CheckBox(context);
                    checkBox.setText(v[i]);
                    gridLayout.addView(checkBox);
                    if (editable.get(viewType).equals("0")) {
                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkBox.setChecked(false);
                            }
                        });
                    }
                }
                return new viewHolder(view);
            } else {
                for (int j = 0; j < v.length; j++) {
                    GridLayout gridLayout = view1.findViewById(R.id.radip_grid);
                    RadioButton radioButton = new RadioButton(context);
                    radioButton.setText(v[j]);
                    radioButton.setTextSize(12);
                    radioGroup.addView(radioButton);
                    if (editable.get(viewType).equals("0")) {
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                radioGroup.clearCheck();
                            }
                        });
                    }
                }
                return new viewHolder(view1);
            }
        } else if (typeId.get(viewType).equals("5")) {
            View view = layoutInflater.inflate(R.layout.camera_layout, parent, false);
            LinearLayout linearLayout = view.findViewById(R.id.liner_cam);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            Dialog dialog;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.list);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Open Camera");
            arrayList.add("Open Gallery");
            int s = Integer.parseInt(size.get(viewType));

            for (int i = 0; i < s; i++) {
                imageView = new ImageView(context);
                imageView.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_photo_camera_24));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                        ListView listView = dialog.findViewById(R.id.list1);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
                        listView.setAdapter(arrayAdapter);
                        Camera_Gallery camera_gallery = new Camera_Gallery(imageView, v.getContext());
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        try {
                                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                            context.startActivity(intent);
//                                            androidx.activity.result.ActivityResult s = new androidx.activity.result.ActivityResult(RESULT_OK,intent);
//                                            Object data = s.getData().getExtras().get("data");
//
//                                            Bitmap bitmap = (Bitmap) data;
//                                            imageView.setImageBitmap(bitmap);
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            Toast.makeText(context, "Exception" + e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                if (position == 1) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                            intent.setType("image/*");
                                            context.startActivity(intent);
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            Toast.makeText(context, "Exception" + e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
                linearLayout.addView(imageView, lp);
            }
            return new viewHolder(view);

        } else if (typeId.get(viewType).equals("6")) {
            View view = layoutInflater.inflate(R.layout.signature_layout, parent, false);
            SignaturePad signaturePad = view.findViewById(R.id.signature_pad);
            if (editable.get(viewType).equals("0")) {
                signaturePad.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        signaturePad.clear();
                        return false;
                    }
                });
            }
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("7")) {
            View view = layoutInflater.inflate(R.layout.date_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("8")) {
            View view = layoutInflater.inflate(R.layout.rating_layout, parent, false);
            RatingBar ratingBar = view.findViewById(R.id.ratingbar);
            ratingBar.setNumStars(Integer.parseInt(size.get(viewType)));
            if (editable.get(viewType).equals("0")) {
                ratingBar.setClickable(false);
            }
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("9")) {
            View view = layoutInflater.inflate(R.layout.slider_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("10")) {
            View view = layoutInflater.inflate(R.layout.spinner_layout, parent, false);
//            View view2 = layoutInflater.inflate(R.layout.smartspinner_layout, parent, false);
            String[] s = value.get(viewType);
            TextView textView;
            Dialog dialog;
            textView = view.findViewById(R.id.tv);
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.search);
            dialog.getWindow().setLayout(650, 650);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
                    EditText editText = dialog.findViewById(R.id.edit_text);
                    ListView listView = dialog.findViewById(R.id.list_view);
                    // Initialize array adapter
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, s);
                    // set adapter
                    listView.setAdapter(arrayAdapter);
//                close.setOnClickListener(onClickListener);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            arrayAdapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            textView.setText(arrayAdapter.getItem(position));

                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            });
//            else {
//                Dialog dialog = new Dialog(context);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(350, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp.gravity= Gravity.CENTER;
//                dialog.addContentView(view,lp);
//            }
        return new viewHolder(view);

     }else if (typeId.get(viewType).equals("11")) {
            View view = layoutInflater.inflate(R.layout.fingerprint_checkpoint_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("12")) {
            View view = layoutInflater.inflate(R.layout.video_capture_checkpoint_layout, parent, false);
            ImageView btn = view.findViewById(R.id.et1);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(context, permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                            try {
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                context.startActivity(intent);
//                                scanner_activity.New();
                            } catch (Exception e) {
                                Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission.CAMERA}, 100);

                        }
                    }
                }
            });
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("13")) {
            View view = layoutInflater.inflate(R.layout.address_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("14")) {
            View view = layoutInflater.inflate(R.layout.mail_id_layout, parent, false);
            EditText editText = view.findViewById(R.id.et1);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!s.toString().contains("@")){
                        editText.setError("Text is not an Email address");
                    }
                }
            });
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("15")) {
            View view = layoutInflater.inflate(R.layout.bar_code_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("16")) {
            View view = layoutInflater.inflate(R.layout.url_layout, parent, false);
            TextView url = view.findViewById(R.id.et1);
            url.setText(value.get(viewType)[0]);
            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Webview.class);
                    intent.putExtra("score",score.get(viewType));
//                    Toast.makeText(context, "Score" + score.get(viewType), Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });
//            url.setMovementMethod(LinkMovementMethod.getInstance());
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("17")) {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("18")) {
            View view = layoutInflater.inflate(R.layout.video_display_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("19")) {
            View view = layoutInflater.inflate(R.layout.image_display_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("20")) {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("21")) {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        } else if (typeId.get(viewType).equals("22")) {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.name_layout, parent, false);
            return new viewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView t = holder.itemView.findViewById(R.id.tv_name);
//        Toast.makeText(context, "position"+String.valueOf(position+1), Toast.LENGTH_LONG).show();
        t.setText(String.valueOf(position+1)+". "+description.get(position));
        TextView e = holder.itemView.findViewById(R.id.datetv1);;
        t.setTextSize(15);

        ImageView image_diaplay = holder.itemView.findViewById(R.id.image_display);
        if(typeId.get(position).equals("19")){
            String s = value.get(position)[0];
            Picasso.get().load(s).into(image_diaplay);
        }

        if(typeId.get(position).equals("18")){
            String s =value.get(position)[0];
//            String s = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
            VideoView videoView = holder.itemView.findViewById(R.id.videoPoint);
            Uri uri = Uri.parse(s);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);
//            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
//        if (checkpointid.get(position).equals("19")){
//
//        }
//        if (typeId.get(position).equals("4")){
//
//            LinearLayout linearLayout = holder.itemView.findViewById(R.id.lout);
//            String[] s = value.get(position);
//            CheckBox checkBox = new CheckBox(context);
//            checkBox.setText(s[0]);
//            linearLayout.addView(checkBox);
//            String[] s =value.get(v);
//            makeText(context, "values: "+value, Toast.LENGTH_SHORT).show();
//            LinearLayout linearLayout = holder.itemView.findViewById(R.id.lout);
//            linearLayout = linearLayout.getParent();
//            int length = s.length;
//            int i =0;
//            int j =0;
//                for (int i=0;i<s.length;i++){
//                    CheckBox checkBox = new CheckBox(context);
//                    checkBox.setText(s[i]);
//                    linearLayout.addView(checkBox);
//                    i++;
//                }
//        }

        if (typeId.get(position).equals("7")) {
            ImageView btn = holder.itemView.findViewById(R.id.datebtn);
            final Calendar calendar = Calendar.getInstance();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    e.setText(day+ "/"+month+1+ "/"+year);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                            e.setText(d + "/" + m+1 + "/" + y);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            });
        }else if (typeId.get(position).equals("12")) {
            ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.et1);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission
                                (context, permission.CAMERA) !=
                                PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission.CAMERA}, 0);

                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                            startActivityForResult(new Activity(), intent, 0, null);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return checkpointid.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

