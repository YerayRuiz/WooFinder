package com.example.woofinder.clases;

import android.nfc.Tag;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.OrganizacionActivity;
import com.example.woofinder.OrganizacionAdapter;
import com.example.woofinder.PruebaActivity;
import com.example.woofinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Organizacion implements Serializable {
    private FirebaseFirestore db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
    private CollectionReference organizacionCollection = db.collection("Organizacion");
    private String id;
    private String cif;
    private String correo;
    private String nombre;

    public Organizacion(){
    }

    public Organizacion(String cif, String correo, String nombre) {
        Map<String, Object> data = new HashMap<>();
        data.put("cif", cif);
        data.put("correo", correo);
        data.put("nombre", nombre);

        DocumentReference newRef = db.collection("Organizacion").document();
        newRef.set(data);

        this.id = newRef.getId();
        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getCif() {
        return cif;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
    @Override
    public String toString() {
        return "Organizacion{" +
                "id='" + id + '\'' +
                ", cif='" + cif + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
    */

    public void updateOrganizacion(String cif, String correo, String nombre){
        Map<String, Object> organizacion = new HashMap<>();
        organizacion.put("cif", cif);
        organizacion.put("correo", correo);
        organizacion.put("nombre", nombre);

        organizacionCollection.document(this.id).set(organizacion);

        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
    }

    public void deleteOrganizacion(){
        organizacionCollection.document(this.id).delete();
        this.id = null;
        this.cif = null;
        this.correo = null;
        this.nombre = null;
    }

    @Override
    public String toString() {
        return "Organizacion{" +
                "path='" + id + '\'' +
                ", cif='" + cif + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
