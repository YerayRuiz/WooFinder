package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {
    static FirebaseFirestore db = SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private static CollectionReference organizacionCollection = db.collection("Organizacion");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        organizacionCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Organizacion> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Organizacion org = document.toObject(Organizacion.class);
                        org.setId(document.getId());
                        list.add(org);
                    }
                    ListView listaOrganizaciones = (ListView) findViewById(R.id.listaOrganizaciones);
                    OrganizacionAdapter organizacionAdapter = new OrganizacionAdapter(getBaseContext(), list);
                    listaOrganizaciones.setAdapter(organizacionAdapter);

                    listaOrganizaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Organizacion org = (Organizacion) listaOrganizaciones.getItemAtPosition(i);

                            Toast.makeText(getBaseContext(), org.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("RegistroActivity", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}