package com.example.practice.ForEdwin;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;

public class MyImageViewHolder extends RecyclerView.ViewHolder {

    ImageView imageViewForPicker;
    public MyImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewForPicker=itemView.findViewById(R.id.imageViewForPicker);
    }
}
