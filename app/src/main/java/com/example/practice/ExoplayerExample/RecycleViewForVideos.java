package com.example.practice.ExoplayerExample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.practice.R;
import com.example.practice.ExoplayerExample.model.MediaObject;
import com.example.practice.ExoplayerExample.model.MyVideosList;
import com.example.practice.ExoplayerExample.model.Video;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RecycleViewForVideos extends AppCompatActivity {

    private static final String TAG = RecycleViewForVideos.class.getSimpleName();
    RecyclerView rv;
    MyRecyclerViewAdapter rad;
    RecyclerView.LayoutManager lam;
    ArrayList<MediaObject> mediaObjectArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_for_videos);
        rv=findViewById(R.id.recycleView);

        readJson();

        lam=new LinearLayoutManager(this);
        rv.setLayoutManager(lam);
        rad=new MyRecyclerViewAdapter(mediaObjectArrayList,this);
        rv.setAdapter(rad);
    }
    public void initData()
    {
        MediaObject mo1=new MediaObject("Big Buck Bunny",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1");
        MediaObject mo2=       new MediaObject("Elephant Dream",
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                        "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                        "Description for media object #2");
        MediaObject mo3=        new MediaObject("For Bigger Blazes",
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                        "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                        "Description for media object #3");
        MediaObject mo4=       new MediaObject("For Bigger Escape",
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                        "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                        "Description for media object #4");
         MediaObject mo5=       new MediaObject("For Bigger Fun",
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                        "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                        "Description for media object #5");
        mediaObjectArrayList=new ArrayList<>(Arrays.asList(mo1,mo2,mo3,mo4,mo5));
    }
    public  void initJsonData() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.video_list);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        Log.d(TAG,"jsonString::"+jsonString);
        MyVideosList myVideosList=new Gson().fromJson(jsonString, MyVideosList.class);
        System.out.println("myVideosList::"+myVideosList.toString());
        List<Video> videosList=myVideosList.getVideos();

    }
    public void readJson()
    {

        InputStream inputStream=getResources().openRawResource(R.raw.video_list);
        String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
       // Log.d(TAG,"jsonString::"+jsonString);
        MyVideosList myVideosList=new Gson().fromJson(jsonString, MyVideosList.class);
        List<Video> videoList=myVideosList.getVideos();
        mediaObjectArrayList=new ArrayList<>();
        for(Video v:videoList) {
            MediaObject mediaObject=new MediaObject(v.getTitle(),v.getSources().get(0),v.getThumb(),v.getDescription());
            mediaObjectArrayList.add(mediaObject);
        }
    }
}