package com.example.twitchflix;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StreamPlayer extends AppCompatActivity {

    private FloatingActionButton btn_backStream;
    private TextView txt_titleSP,txt_usernameSP;
    private VideoView streamPlayer;
    private ImageView btn_fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_player);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        txt_titleSP = findViewById(R.id.txt_titleSP);
        txt_usernameSP = findViewById(R.id.txt_usernameSP);

        Stream s = (Stream) b.getSerializable("streamData");
        txt_titleSP.setText(s.getTitle());
        txt_usernameSP.setText(s.getUsername());
        Uri url = Uri.parse(s.getStreamURL());
        streamPlayer.setVideoURI(url);

    }
}