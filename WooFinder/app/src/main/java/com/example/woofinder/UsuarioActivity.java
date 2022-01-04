package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.Usuario;


public class UsuarioActivity extends AppCompatActivity {
    private Button btnAddUsuario;
    private Button btnDeleteUsuario;
    private Button btnListarUsuario;
    private Usuario usuario= new Usuario("yeray.r7@uma.es","Yeray", new Organizacion(), "yeraymakinon");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        enlazarControles();
    }

    private void enlazarControles() {
        this.btnAddUsuario = findViewById(R.id.btnAddUsuario);
        this.btnAddUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.updateUsuario("", "", new Organizacion(), "");
            }
        });

        this.btnDeleteUsuario = findViewById(R.id.btnDeleteUsuario);
        this.btnDeleteUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.deleteUsuario();
            }
        });

    }
}