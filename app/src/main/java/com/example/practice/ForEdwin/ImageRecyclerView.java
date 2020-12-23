package com.example.practice.ForEdwin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.practice.R;

import java.util.ArrayList;

public class ImageRecyclerView extends AppCompatActivity {

    private static final int CODE_MULTIPLE_IMG_GALLERY = 990;
    private static final String TAG = "ImageRecyclerView";
    ArrayList<Uri> uriArrayList;
    MyImageViewRecyclerAdapter myImageViewRecyclerAdapter;
    RecyclerView.LayoutManager lam;
    RecyclerView recyclerViewForImages;
    Button pickImagesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recycler_view);
        recyclerViewForImages = findViewById(R.id.recyclerViewForImages);
        pickImagesButton = findViewById(R.id.pickImagesButton);
        lam = new LinearLayoutManager(this);
        recyclerViewForImages.setLayoutManager(lam);
        uriArrayList = new ArrayList<>();
        Resources resources = this.getResources();
        Uri uriInitialImage = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.ic_launcher_foreground))
                .appendPath(resources.getResourceTypeName(R.drawable.ic_launcher_foreground))
                .appendPath(resources.getResourceEntryName(R.drawable.ic_launcher_foreground))
                .build();

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
              //  viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null && viewHolder.itemView != null) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.exo_gray));
                    } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.black));
                    } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                        viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    }
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Uri uri=uriArrayList.get(viewHolder.getAdapterPosition());
                uriArrayList.remove(viewHolder.getAdapterPosition());
                uriArrayList.add(target.getAdapterPosition(),uri);
                myImageViewRecyclerAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT || direction ==ItemTouchHelper.RIGHT){
                    uriArrayList.remove(position);
                  myImageViewRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
       itemTouchHelper.attachToRecyclerView(recyclerViewForImages);
        Log.d(TAG,"uriInitialImage:"+uriInitialImage.toString());
        myImageViewRecyclerAdapter = new MyImageViewRecyclerAdapter(uriArrayList, this);
        recyclerViewForImages.setAdapter(myImageViewRecyclerAdapter);
        recyclerViewForImages.addItemDecoration(new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        uriArrayList.add(uriInitialImage);
        pickImagesButton.setOnClickListener(v -> {
            pickMultipleImages();

        });


    }

    private void pickMultipleImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CODE_MULTIPLE_IMG_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();

            if (clipData != null && (clipData.getItemCount() > 0)) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    uriArrayList.add(item.getUri());
                    Log.d("new uri in list", String.valueOf(item.getUri()));
                }
                myImageViewRecyclerAdapter.notifyDataSetChanged();
            }  else if (data.getData() != null) {
                uriArrayList.add(data.getData());
                myImageViewRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

}