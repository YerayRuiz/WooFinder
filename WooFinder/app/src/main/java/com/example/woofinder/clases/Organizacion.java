package com.example.woofinder.clases;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woofinder.OrganizacionActivity;
import com.example.woofinder.PruebaActivity;
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

import java.util.HashMap;
import java.util.Map;

public class Organizacion {
    private static FirebaseFirestore db = SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private static CollectionReference organizacionCollection = db.collection("Organizacion");
    private String path;
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

        this.path = newRef.getId();
        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
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

    public void setPath(String path) {
        this.path = path;
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

    public void updateOrganizacion(String cif, String correo, String nombre){
        Map<String, Object> organizacion = new HashMap<>();
        organizacion.put("cif", cif);
        organizacion.put("correo", correo);
        organizacion.put("nombre", nombre);

        organizacionCollection.document(this.path).set(organizacion);

        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
    }

    public void deleteOrganizacion(String path){
        organizacionCollection.document(path).delete();
        this.path = null;
        this.cif = null;
        this.correo = null;
        this.nombre = null;
    }

    @Override
    public String toString() {
        return "Organizacion{" +
                "path='" + path + '\'' +
                ", cif='" + cif + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
