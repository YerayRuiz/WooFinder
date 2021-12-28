package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.Solicitud;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class AnimalActivity extends AppCompatActivity {

    private Button btnAddAnimal;
    private Button btnDeleteAnimal;
    private Animal animal= new Animal("galguito", new Timestamp(new Date()), new GeoPoint(1, 1), "PERRO");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        enlazarControles();
    }

    private void enlazarControles() {
        this.btnAddAnimal = findViewById(R.id.btnAddAnimal);
        this.btnAddAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal.addAnimal();
            }
        });

        this.btnDeleteAnimal = findViewById(R.id.btnDeleteAnimal);
        this.btnDeleteAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal.deleteAnimal();
            }
        });
    }
}