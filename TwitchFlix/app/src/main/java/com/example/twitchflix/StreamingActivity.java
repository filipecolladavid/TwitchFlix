package com.example.twitchflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StreamingActivity extends AppCompatActivity {

    private TextView txt_StreamTitleValue;
    private Button btn_StartStreaming;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);

        Bundle b = getIntent().getExtras();

        username = b.getString("sessionUser");

        txt_StreamTitleValue = findViewById(R.id.txt_StreamTitleValue);
        btn_StartStreaming = findViewById(R.id.btn_StartStreaming);

        /**
         * Start StreamActivity and pass StreamTitleValue and Session username
         */
        btn_StartStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreamingActivity.this,newStreamActivity.class);
                intent.putExtra("sessionUser",username);
                intent.putExtra("streamTitle",txt_StreamTitleValue.getText().toString());
                startActivity(intent);
            }
        });
    }
}