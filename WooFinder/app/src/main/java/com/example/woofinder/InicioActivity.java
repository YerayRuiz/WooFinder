package com.example.woofinder;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonOrganizacion;

import java.util.Locale;

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
                Intent i = new Intent(getApplicationContext(),LoginOrganizacionActivity.class);
                startActivity(i);
            }
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
                System.exit(0);
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}