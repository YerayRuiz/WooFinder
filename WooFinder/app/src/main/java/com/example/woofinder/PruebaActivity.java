package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.woofinder.clases.Organizacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.woofinder.clases.SingletonDataBase;

public class PruebaActivity extends AppCompatActivity {
    // Creamos la database al inicializar la main activity y se lo añadimos al mapa singleton con la SHARED_KEY en onCreate.

    private static final  FirebaseFirestore db= FirebaseFirestore.getInstance();
    public static final String SHARED_DATA_KEY= "SHARED_DATABASE_KEY";



    private TextView nombreTextView;
    private TextView correoTextView;
    private CollectionReference crf;

    private Button buttonUsuarios;
    private Button buttonSolicitudes;
    private Button buttonAnimales;
    private Button buttonOrganizaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        // Al inicializar la primera actividad, hay que crear la instancia de la base de datos.
        SingletonDataBase.getInstance().put(SHARED_DATA_KEY, db);


        enlazarControles();
    /*
        db.collection("Usuario").document("M1t0L1c1TOdRuaMngINs").get().addOnCompleteListener
                (new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                funcion(doc);
            }
        });*/
        this.crf= db.collection("Usuario");
        Task<QuerySnapshot> q = crf.whereEqualTo("correo", "joseluischulo@gmail.com").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    /*DocumentSnapshot document = task.getResult();
                      if (document.exists()){
                          System.out.println(d.getData().get("nombre"));
                      } else {
                          System.out.println("No existe el documento");
                      }
                     */
                {for (QueryDocumentSnapshot d : task.getResult()){
                    System.out.println(d.getData().get("nombre"));
                    }
                }
            }
        });
    }

    private void funcion(QueryDocumentSnapshot documentSnapshot) {
        //this.nombreTextView.setText((String) documentSnapshot.getData());
        this.correoTextView.setText((String) documentSnapshot.get("correo"));
    }


     ///TODO: QUE CADA UNO ENLACE CON SU ACTIVITY
    private void enlazarControles() {
        this.nombreTextView = findViewById(R.id.nombreTextView);
        this.correoTextView = findViewById(R.id.correoTextView);

        this.buttonSolicitudes=findViewById(R.id.buttonSolicitud);
        this.buttonSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SolicitudActivity.class);
                startActivity(i);
            }
        });

        this.buttonUsuarios=findViewById(R.id.buttonUsuario);
        this.buttonUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UsuarioActivity.class);
                startActivity(i);
            }
        });

        this.buttonAnimales =findViewById(R.id.buttonAnimal);
        this.buttonAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AnimalActivity.class);
                startActivity(i);
            }
        });

        this.buttonOrganizaciones = findViewById(R.id.buttonOrganizacion);
        this.buttonOrganizaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}