package com.example.practice.ForEdwin;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;

import java.util.ArrayList;

public class MyImageViewRecyclerAdapter extends RecyclerView.Adapter<MyImageViewHolder> {
    ArrayList<Uri> uriArrayList;
    MyImageViewHolder myImageViewHolder;
    Context context;
    View v;

    public MyImageViewRecyclerAdapter(ArrayList<Uri> uriArrayList, Context context) {
        this.uriArrayList = uriArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v=LayoutInflater.from(context).inflate(R.layout.image_view_list,parent,false);
        myImageViewHolder=new MyImageViewHolder(v);
        return myImageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyImageViewHolder holder, int position) {
        holder.imageViewForPicker.setImageURI(uriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }
}
