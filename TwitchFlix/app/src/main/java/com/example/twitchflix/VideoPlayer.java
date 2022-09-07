package com.example.twitchflix;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VideoPlayer extends AppCompatActivity {

    private FloatingActionButton btn_back;
    private TextView txt_titleVPValue;
    private VideoView videoPlayer;
    private ImageView btn_fullScreen;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        txt_titleVPValue = findViewById(R.id.txt_titleVPValue);
        btn_back = findViewById(R.id.btn_back);
        btn_fullScreen = findViewById(R.id.btn_fullScreen);
        videoPlayer = findViewById(R.id.videoPlayer);
        frameLayout = findViewById(R.id.frameLayout);

        Movie m = (Movie) b.getSerializable("movieData");
        txt_titleVPValue.setText(m.getTitle());
        Uri url = Uri.parse(m.getLink());
        videoPlayer.setVideoURI(url);

        MediaController mc = new MediaController(this);
        videoPlayer.setMediaController(mc);

        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPlayer.start();
            }
        });

        btn_fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - implement fullscreen functionality
            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}