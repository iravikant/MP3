package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {

    boolean play;
    Button buttonPlayPause,buttonPrevious,buttonNext;
    SeekBar musicSeekBar;
    String Mp3 = "";
    TextView tvFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonPlayPause = findViewById(R.id.buttonPlayPause);
        musicSeekBar = findViewById(R.id.musicSeekBar);
       tvFile = findViewById(R.id.tvFile);


        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        String aPath = "android.resource://"+getPackageName()+"/raw/pyaar";

        Uri audioURI = Uri.parse(aPath);
        try {
            mp.setDataSource(this,audioURI);
            mp.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()){
                    buttonPlayPause.setBackgroundResource(R.drawable.pause);
                    mp.start();
                }else {
                    buttonPlayPause.setBackgroundResource(R.drawable.play);
                    mp.pause();
                }


            }
        });
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.getDuration();
                mp.seekTo(0);

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        musicSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.getCurrentPosition();
                mp.getDuration();

            }
        });
    }
}