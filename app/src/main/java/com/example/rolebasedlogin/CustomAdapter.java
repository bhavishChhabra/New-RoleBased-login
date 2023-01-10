package com.example.rolebasedlogin;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;

    public CustomAdapter(Context context, ArrayList<String> caption, ArrayList<String> icon) {
        this.context = context;
        this.caption = caption;
        Icon = icon;
    }

    private ArrayList<String> caption = new ArrayList<>();
    private ArrayList<String> Icon = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewlayout,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.description.setText(caption.get(position));
//        Picasso.get().load(my_data.get(position).getImage_link()).into(holder.imageView);
        Glide.with(context).load(Icon.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return caption.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView description;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
