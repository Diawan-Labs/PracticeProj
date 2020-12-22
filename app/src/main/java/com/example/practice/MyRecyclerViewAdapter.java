package com.example.practice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.model.MediaObject;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    MyRecyclerViewHolder vh;
    ArrayList<MediaObject> mediaObjectArrayList;
    Context mContext;
    View v;
    public MyRecyclerViewAdapter(ArrayList<MediaObject> mediaObjectArrayList, Context mContext) {
        this.mediaObjectArrayList = mediaObjectArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v= LayoutInflater.from(mContext).inflate(R.layout.item_view_list,parent,false);
        vh=new MyRecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        MediaObject mo=mediaObjectArrayList.get(position);
        holder.videoTitle.setText(mo.getTitle());

        MediaItem mediaItem = MediaItem.fromUri(mo.getMedia_url());
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(mContext).build();
        player.setMediaItem(mediaItem);
        holder.styledPlayerView.setPlayer(player);
        player.prepare();
        //player.play();
        player.setPlayWhenReady(false);

    }

    @Override
    public int getItemCount() {
        return mediaObjectArrayList.size();
    }
}
