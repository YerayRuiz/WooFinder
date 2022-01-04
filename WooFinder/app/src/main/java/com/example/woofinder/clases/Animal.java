package com.example.woofinder.clases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.PruebaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animal implements Serializable {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
    private CollectionReference animalCollection = db.collection("Animal");
    private String id;
    private String descripcion;
    private Timestamp fecha;
    private GeoPoint localizacion;
    private String tipo;

    public Animal() {

    }

    public Animal(String descripcion, Timestamp fecha, GeoPoint localizacion, String tipo){
        Map<String, Object> data = new HashMap<>();
        data.put("descripcion", descripcion);
        data.put("fecha", fecha);
        data.put("localizacion", localizacion);
        data.put("tipo", tipo);

        DocumentReference newRef = db.collection("Animal").document();
        newRef.set(data);

        this.id = newRef.getId();
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.localizacion = localizacion;
        this.tipo = tipo;
    }

    public void updateAnimal(String descripcion, Timestamp fecha, GeoPoint localizacion, String tipo){
        Map<String, Object> animal = new HashMap<>();
        animal.put("descripcion", descripcion);
        animal.put("fecha", fecha);
        animal.put("localizacion", localizacion);
        animal.put("tipo", tipo);;

        animalCollection.document(this.id).set(animal);

        this.descripcion = descripcion;
        this.fecha = fecha;
        this.localizacion = localizacion;
        this.tipo = tipo;
    }

    public void deleteAnimal(){
        animalCollection.document(this.id).delete();
        this.id = null;
        this.descripcion = null;
        this.fecha = null;
        this.localizacion = null;
        this.tipo = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public GeoPoint getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(GeoPoint localizacion) {
        this.localizacion = localizacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", localizacion=" + localizacion +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
