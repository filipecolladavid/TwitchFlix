package com.example.twitchflix;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txt_usernameValue, txt_passwordValue;
    private Button btn_login, btn_register;
    private int flag = 0;

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    private User sessionUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_usernameValue.getText().toString();
                String pwd = txt_passwordValue.getText().toString();
                if (haveNetworkConnection()) {
                    flag = doRequest(username, pwd,"login");
                    switch (flag) {
                        case 400:
                            Toast.makeText(MainActivity.this, "Fields are invalid", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "Unknown Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            User u = new User(username);
                            setSessionUser(u);
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainAppMenuActivity.class);
                            intent.putExtra("sessionUser",getSessionUser().getUsername());
                            startActivity(intent);
                            break;
                    }
                } else
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_usernameValue.getText().toString();
                String pwd = txt_passwordValue.getText().toString();
                if (haveNetworkConnection()) {
                    flag = doRequest(username, pwd,"register");
                    switch (flag) {
                        case 409:
                            Toast.makeText(MainActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "Unknown Server Error", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            User u = new User(username);
                            setSessionUser(u);
                            Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int doRequest(String username, String pwd, String path) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("nick", username)
                .add("pwd", pwd)
                .build();
        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url("http://10.0.2.2:8080/user/"+path)
                .post(formBody)
                .build();

        System.out.println(request);
        System.out.println(request.body().toString());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                flag = response.code();
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }

    boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void initViews() {
        txt_usernameValue = findViewById(R.id.txt_usernameValue);
        txt_passwordValue = findViewById(R.id.txt_passwordValue);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
    }
}