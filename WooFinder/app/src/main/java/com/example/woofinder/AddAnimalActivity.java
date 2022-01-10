package com.example.woofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.SingletonPoint;
import com.example.woofinder.fragments.ListaAnimalFragment;
import com.example.woofinder.fragments.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class AddAnimalActivity extends AppCompatActivity {


    private Button btnNuevoAnimal;
    private EditText txtNombreAnimal;
    private EditText txtTipoAnimal;

    public String getTxtNombreAnimalString() {
        return txtNombreAnimal.getText().toString();
    }

    public String getTxtTipoAnimalString() {
        return txtTipoAnimal.getText().toString();
    }

    public Button getBtnNuevoAnimal() {
        return btnNuevoAnimal;
    }

    public EditText getTxtTipoAnimal() {
        return txtTipoAnimal;
    }

    public EditText getTxtNombreAnimal() {
        return txtNombreAnimal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        this.btnNuevoAnimal=findViewById(R.id.btnNuevoAnimal2);
        this.txtNombreAnimal = findViewById(R.id.txtnameAnimal2);
        this.txtTipoAnimal = findViewById(R.id.txtTypeAnimal2);

        this.btnNuevoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng point = SingletonPoint.getInstance().get("POINT");
                if (point == null) {
                    Toast.makeText(getApplicationContext(),
                            "Click en el mapa por favor", Toast.LENGTH_LONG).show();
                } else {
                    Timestamp t = new Timestamp(new Date());
                    GeoPoint g = new GeoPoint(point.latitude, point.longitude);
                    Animal a = new Animal(getTxtNombreAnimalString(), t, g, getTxtTipoAnimalString());
                    Toast.makeText(getApplicationContext(),
                            "Animal creado exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }
}
