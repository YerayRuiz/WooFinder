package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Solicitud;

public class SolicitudActivity extends AppCompatActivity {


    private Button bAddSolicitud;
    private Button bDeleteSolicitud;
    private Solicitud solicitud= new Solicitud("prueboncia","otra prueboncia");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        enlazarControles();
    }

    private void enlazarControles() {
        this.bAddSolicitud = findViewById(R.id.bAddSolicitud);
        this.bAddSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitud.addSolicitud();
            }
        });
        this.bDeleteSolicitud = findViewById(R.id.bBorrarSolicitud);
        this.bDeleteSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitud.deleteSolicitud();
            }
        });
    }
}