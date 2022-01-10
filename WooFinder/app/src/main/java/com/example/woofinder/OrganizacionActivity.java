package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Organizacion;

public class OrganizacionActivity extends AppCompatActivity {

    Button btnUpdateOrganizacion;
    Button btnDeleteOrganizacion;
    private Organizacion organizacion = new Organizacion("3452", "xuxos@gmail.com", "xuxos sin fronteras");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizacion);
        enlazarControles();
    }

    private void enlazarControles() {
        this.btnUpdateOrganizacion = findViewById(R.id.btnUpdateOrganizacion);
        this.btnUpdateOrganizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizacion.updateOrganizacion("2341", "chuchos@gmail.com", "chuchos sin fronteras", "xuxitos");
            }
        });

        this.btnDeleteOrganizacion = findViewById(R.id.btnDeleteOrganizacion);
        this.btnDeleteOrganizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizacion.deleteOrganizacion();
            }
        });
    }


}