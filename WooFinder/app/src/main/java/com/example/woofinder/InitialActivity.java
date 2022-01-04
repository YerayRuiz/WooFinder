package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.woofinder.clases.SingletonDataBase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class InitialActivity extends AppCompatActivity {

    public static final FirebaseFirestore db= FirebaseFirestore.getInstance();
    public static final String SHARED_DATA_KEY= "SHARED_DATABASE_KEY";
    private Timer timer = new Timer() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intial);
        final MediaPlayer mp3= MediaPlayer.create(this, R.raw.woof );
        mp3.start();
        //Para inicializar la bd
        SingletonDataBase.getInstance().put(SHARED_DATA_KEY, db);
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