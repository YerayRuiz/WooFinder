package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class InitialActivity extends AppCompatActivity {

    private Timer timer = new Timer() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intial);
        final MediaPlayer mp3= MediaPlayer.create(this, R.raw.woof );
        mp3.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent it = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(it);
                finish();
            }
        }, 1500);
    }
}