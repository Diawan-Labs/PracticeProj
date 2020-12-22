package com.example.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.practice.databinding.ActivityMainBinding;

import java.io.File;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private final int REQUEST_CAPTURE_PHOTO=101;
    private final int REQUEST_CAPTURE_VIDEO=102;
    private final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=activityMainBinding.getRoot();
        setContentView(view);
        activityMainBinding.capturePhoto.setOnClickListener(v->doCapturePhoto());
        activityMainBinding.captureVideo.setOnClickListener(v->doCaptureVideo());
    }

    private void doCaptureVideo() {
        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/myvideo.mp4");
        Uri fileUri = Uri.parse(mediaFile.getPath());
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent,REQUEST_CAPTURE_VIDEO);
    }

    private void doCapturePhoto() {


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        startActivityForResult(intent,REQUEST_CAPTURE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==REQUEST_CAPTURE_PHOTO)
        {
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            activityMainBinding.imageView.setImageBitmap(bitmap);
        }
       else if(resultCode==RESULT_OK&&requestCode==REQUEST_CAPTURE_VIDEO)
        {
            Log.d(TAG, String.valueOf(data.getData()));
        }
    }
}