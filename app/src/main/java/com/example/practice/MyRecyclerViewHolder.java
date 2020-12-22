package com.example.practice;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView videoTitle;
    PlayerView styledPlayerView;
    ImageView thumbnail;
    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        videoTitle=itemView.findViewById(R.id.videoTitle);
        styledPlayerView=itemView.findViewById(R.id.styledPlayerView);
        thumbnail=itemView.findViewById(R.id.thumbnail);
    }
}
