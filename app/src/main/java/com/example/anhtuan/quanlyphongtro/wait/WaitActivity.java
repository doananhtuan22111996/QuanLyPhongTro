package com.example.anhtuan.quanlyphongtro.wait;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.login.LogInActivity;
import com.example.anhtuan.quanlyphongtro.main.MainActivity;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        Thread wait = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
                    if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
                        Intent intent = new Intent(WaitActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(WaitActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        wait.start();
    }
}
