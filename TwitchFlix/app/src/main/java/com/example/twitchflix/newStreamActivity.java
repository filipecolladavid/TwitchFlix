package com.example.twitchflix;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.pedro.rtmp.utils.ConnectCheckerRtmp;
import com.pedro.rtplibrary.rtmp.RtmpCamera1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class newStreamActivity extends AppCompatActivity implements ConnectCheckerRtmp {

    private TextView et_streamName;
    private Button btn_start_stop;
    private String username;
    private String streamTitle;
    private RtmpCamera1 rtmpCamera1;
    private boolean isInit;
    String stream_url = "rtmp://127.0.0.1:1935/live/";

    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stream);

        Bundle b = getIntent().getExtras();
        isInit = false;

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else if (!isInit) {
            username = b.getString("sessionUser");
            streamTitle = b.getString("streamTitle");
            SurfaceView surfaceView = findViewById(R.id.surfaceView);
            rtmpCamera1 = new RtmpCamera1(surfaceView, this);
            et_streamName = findViewById(R.id.txt_StreamTitleValue);
            btn_start_stop = findViewById(R.id.btn_start_stop);
            isInit = true;
            btn_start_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!rtmpCamera1.isStreaming()) {
                        if (rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                            rtmpCamera1.startStream(stream_url + sharedPreferences.getString("username", ""));
                            btn_start_stop.setText("Stop Stream");
                            btn_start_stop.setEnabled(false);
                            StreamNotify("add");

                        } else {
                            Toast.makeText(newStreamActivity.this, "This device can't start the stream", Toast.LENGTH_SHORT);
                            btn_start_stop.setEnabled(true);
                        }
                    } else {
                        rtmpCamera1.stopStream();
                        btn_start_stop.setText("Start Stream");
                        btn_start_stop.setEnabled(false);
                        StreamNotify("remove");
                    }
                }
            });
        }
    }

    /**
     * Notifies server about stream
     *
     * @param post - add or remove
     */
    private void StreamNotify(String post) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("nick", username)
                .add("title", streamTitle)
                .build();

        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(stream_url + username + "_" + streamTitle)
                .post(formBody)
                .build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onAuthErrorRtmp() {
        runOnUiThread(() -> {
            Toast.makeText(newStreamActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            btn_start_stop.setEnabled(true);
        });
    }

    @Override
    public void onAuthSuccessRtmp() {
        runOnUiThread(() -> {
            Toast.makeText(newStreamActivity.this, "Auth success", Toast.LENGTH_SHORT).show();
            btn_start_stop.setEnabled(true);
        });

    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(() -> {
            Toast.makeText(newStreamActivity.this, "Connection failed. " + reason, Toast.LENGTH_SHORT)
                    .show();
            btn_start_stop.setEnabled(false);
            rtmpCamera1.stopStream();
            btn_start_stop.setText("Start Stream");
            StreamNotify("remove");
        });
    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(() -> {
            Toast.makeText(newStreamActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            btn_start_stop.setEnabled(true);
        });
    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(() -> {
            Toast.makeText(newStreamActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            btn_start_stop.setEnabled(true);
        });
    }

    @Override
    public void onNewBitrateRtmp(long l) {

    }

    @Override
    public void onConnectionStartedRtmp(@NonNull String s) {

    }
}