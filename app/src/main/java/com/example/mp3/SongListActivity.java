package com.example.mp3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3.Adapter.SongListAdapter;
import com.example.mp3.databinding.ActivitySongListBinding;

import java.io.File;
import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    ActivitySongListBinding b;

    ArrayList<SongModel> songsList = new ArrayList<>();

    TextView noMedia;
    RecyclerView rvSong;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySongListBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        b.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        if (checkPermission()== false){
            requestPermission();
            return;


        }
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC +"! = 0 ";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);

        while(cursor.moveToNext()){
            SongModel songData = new SongModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if (new File(songData.getPath()).exists())

            songsList.add(songData);

        }

        if (songsList.size() == 0){
            noMedia.setVisibility(View.VISIBLE);
        }else {
            rvSong.setLayoutManager(new LinearLayoutManager(this));
            rvSong.setAdapter(new SongListAdapter(songsList,getApplicationContext()));
        }
    }
    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(SongListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;

        }else{
            return false;
        }

    }
void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(SongListActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(SongListActivity.this,"Storage Permission is Required",Toast.LENGTH_SHORT).show();
        }else
    ActivityCompat.requestPermissions(SongListActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
}
}