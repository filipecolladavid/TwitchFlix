package com.example.twitchflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainAppMenuActivity extends AppCompatActivity {

    private TextView txt_usernameValueMenu;
    private Button btn_vod, btn_streams, btn_streamStart;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_menu);
        Bundle extras = getIntent().getExtras();
        txt_usernameValueMenu = findViewById(R.id.txt_usernameValueMenu);
        username = extras.getString("sessionUser");
        txt_usernameValueMenu.setText(username);
        btn_vod = findViewById(R.id.btn_vod);
        btn_streams = findViewById(R.id.btn_streams);
        btn_streamStart = findViewById(R.id.btn_streamStart);

        btn_vod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppMenuActivity.this,VodCatalogActivity.class);
                startActivity(intent);
            }
        });

        btn_streams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppMenuActivity.this, StreamCatalogActivity.class);
                startActivity(intent);
            }
        });

        btn_streamStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppMenuActivity.this,StreamingActivity.class);
                intent.putExtra("sessionUser", username);
                startActivity(intent);
            }
        });
    }
}