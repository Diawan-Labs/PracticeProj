package com.example.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        Log.d(TAG,"uriInitialImage:"+uriInitialImage.toString());
        uriArrayList.add(uriInitialImage);
        pickImagesButton.setOnClickListener(v -> {
            pickMultipleImages();
            myImageViewRecyclerAdapter = new MyImageViewRecyclerAdapter(uriArrayList, this);
            recyclerViewForImages.setAdapter(myImageViewRecyclerAdapter);
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