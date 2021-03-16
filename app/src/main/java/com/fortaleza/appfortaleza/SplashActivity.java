package com.fortaleza.appfortaleza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask tarea = new TimerTask(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class );
                startActivity(intent);
                finish();
            }

        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 3000);
    }
}