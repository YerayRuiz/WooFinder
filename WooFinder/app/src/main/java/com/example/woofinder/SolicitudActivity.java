package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.Solicitud;

public class SolicitudActivity extends AppCompatActivity {


    private Button btnAddSolicitud;
    private Button btnDeleteSolicitud;
    private Button btnListarSolicitud;
    private Solicitud solicitud= new Solicitud("prueboncia",new Organizacion());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        enlazarControles();
    }

    private void enlazarControles() {
        this.btnAddSolicitud = findViewById(R.id.bAddSolicitud);
        this.btnAddSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitud.updateSolicitud("", new Organizacion());
            }
        });

        this.btnDeleteSolicitud = findViewById(R.id.bBorrarSolicitud);
        this.btnDeleteSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitud.deleteSolicitud();
            }
        });
    }
}