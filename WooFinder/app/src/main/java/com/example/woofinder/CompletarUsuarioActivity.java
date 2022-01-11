package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.woofinder.clases.SingletonUsuario;
import com.example.woofinder.clases.Usuario;

public class CompletarUsuarioActivity extends AppCompatActivity {

    Usuario user;
    TextView correoUsuario;
    EditText nombreUsuario;
    EditText passwordUsuario;
    EditText confirmarPasswordUsuario;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completar_usuario);

        user = SingletonUsuario.getInstance().get("USUARIO");

        correoUsuario = findViewById(R.id.txtEmailUsuario);
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        passwordUsuario = findViewById(R.id.txtPasswordUsuario);
        confirmarPasswordUsuario = findViewById(R.id.txtConfirmPassword);
        btnConfirmar = findViewById(R.id.buttonConfirmar);

        correoUsuario.setText(user.getCorreo());
    }

    public void completarDatos(View view){
        String nombre = nombreUsuario.getText().toString();
        String password = passwordUsuario.getText().toString();
        String confirmarpassword = confirmarPasswordUsuario.getText().toString();

        if (!nombre.equals("")){
            if (!password.equals("")){
                if (password.equals(confirmarpassword)){
                    user.updateUsuario(user.getCorreo(), nombre, user.getOrganizacion(), password);

                    SingletonUsuario.getInstance().put("USUARIO", user);

                    //Para irse a MainActivity
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                } else {
                    confirmarPasswordUsuario.setError(getString(R.string.password_no_coinciden));
                }
            } else {
                passwordUsuario.setError(getString(R.string.password_vacia));
            }
        } else {
            nombreUsuario.setError(getString(R.string.nombre_vacia));
        }
    }
}