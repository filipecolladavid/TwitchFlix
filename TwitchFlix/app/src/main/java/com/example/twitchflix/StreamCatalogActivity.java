package com.example.twitchflix;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StreamCatalogActivity extends AppCompatActivity {

    RecyclerView streamList;
    StreamAdapter adapter;
    List<Stream> sList;
    Boolean responseReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_catalog);
        streamList = findViewById(R.id.streamList);
        streamList.setLayoutManager(new LinearLayoutManager(this));

        sList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/stream/all")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("Received response");
                String jsonData = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for(int i = 0; i<jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        Stream s = new Stream(
                                j.getString("streamURL"),
                                j.getString("username"),
                                j.getString("title"));

                        sList.add(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                responseReady = true;
            }

        });
        while(!responseReady);
        adapter = new StreamAdapter(this,sList);
        streamList.setAdapter(adapter);



    }
}