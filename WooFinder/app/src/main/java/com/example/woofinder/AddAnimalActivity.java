package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.woofinder.clases.Animal;
import com.example.woofinder.fragments.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class AddAnimalActivity extends AppCompatActivity {

    private static LatLng point= new LatLng(1,1);
    private Button btnCreateAnimal;
    private EditText txtDescripcion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        this.btnCreateAnimal = findViewById(R.id.btnCreateAnimal);
        this.txtDescripcion = findViewById(R.id.txtDescripcionAnimal);

        btnCreateAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto= txtDescripcion.getText().toString();
                Timestamp timestamp = new Timestamp(new Date());
                GeoPoint geoPoint = new GeoPoint(point.latitude,point.longitude);
                String tipo = "PERRO";
                Animal animal = new Animal(texto,timestamp,geoPoint,tipo);
                animal.addAnimal();
                finish();
            }
        });
    }

    public static void setPoint(LatLng point) {
         AddAnimalActivity.point=point;
    }

}