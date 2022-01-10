package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonOrganizacion;

public class InicioActivity extends AppCompatActivity {
    Button btnPrueba;
    Button btnMain;
    Button btnLogin;
    Button btnLoginOrganizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        enlazarControles();
    }

    private void enlazarControles() {
        this.btnMain = findViewById(R.id.btnMain);
        this.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddAnimalActivity.class);
                startActivity(i);
            }
        });

        /*
        this.btnPrueba = findViewById(R.id.btnPrueba);
        this.btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),PruebaActivity.class);
                startActivity(i);
            }
        });


         */
        this.btnLogin = findViewById(R.id.buttonLogin);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        this.btnLoginOrganizacion = findViewById(R.id.btnLoginOrganizacion);
        this.btnLoginOrganizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Organizacion org = new Organizacion();
                org.setCif("3452");
                org.setNombre("xuxos sin fronteras");
                org.setCorreo("xuxos@gmail.com");
                org.setId("cVzmV9H0OVedT71nkDJV");
                org.setPassword("xuxito");

                SingletonOrganizacion.getInstance().put("ORGANIZACION", org);

                Intent i = new Intent(getApplicationContext(),OrganizacionActivity.class);
                startActivity(i);
            }
        });
    }
}