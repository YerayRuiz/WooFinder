package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.example.woofinder.clases.SingletonUsuario;
import com.example.woofinder.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference usuarioCollection;
    private CollectionReference organizacionCollection;

    private EditText tEmail;
    private EditText tPassword;
    private Button bLogin;
    private TextView tRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        usuarioCollection = db.collection("Usuario");
        organizacionCollection = db.collection("Organizacion");

        enlazarControles();
    }

    private void enlazarControles() {
        this.tEmail = (EditText) findViewById(R.id.tEmail);
        this.tPassword = (EditText) findViewById(R.id.tPwd);
        this.tRegistro = (TextView) findViewById(R.id.tRegistro);
    }

    public void iniciarSesion(View view){
        String email = tEmail.getText().toString();
        String password = tPassword.getText().toString();

        if (validarEmail(email)){
            usuarioCollection.whereEqualTo("correo", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> listaDoc = task.getResult().getDocuments();

                                if (listaDoc.size() > 0){
                                    DocumentSnapshot document = listaDoc.get(0);
                                    Map<String, Object> map = document.getData();
                                    String pwdUsuario = map.get("password").toString();

                                    if (password.equals(pwdUsuario)) {   //Inicia sesion
                                        Usuario user = new Usuario();
                                        user.setId(document.getId());
                                        user.setCorreo(map.get("correo").toString());
                                        user.setNombre(map.get("nombre").toString());
                                        user.setPassword(map.get("password").toString());
                                        DocumentReference orgRef = (DocumentReference) map.get("organizacion");
                                        orgRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Organizacion organizacion = documentSnapshot.toObject(Organizacion.class);
                                                organizacion.setId(documentSnapshot.getId());
                                                user.setOrganizacion(organizacion);

                                                Toast.makeText(getBaseContext(),
                                                        getString(R.string.usuario_inicio_sesion), Toast.LENGTH_LONG).show();

                                                SingletonUsuario.getInstance().put("USUARIO", user);

                                                if (user.getNombre().equals("")){
                                                    //Para terminar de completar los datos
                                                    Intent i = new Intent(getApplicationContext(),CompletarUsuarioActivity.class);
                                                    startActivity(i);
                                                } else {
                                                    //Para irse a MainActivity
                                                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(i);
                                                }
                                            }
                                        });
                                    } else {
                                        tPassword.setError(getString(R.string.contrasenya_incorrecta));
                                    }
                                } else {
                                    tEmail.setError(getString(R.string.email_no_encontrado));
                                }
                            } else {
                                tEmail.setError(getString(R.string.email_no_encontrado));
                            }
                        }
                    });
        } else {    //Email introducido no valido
            tEmail.setError(getString(R.string.email_no_valido));
        }
    }

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}