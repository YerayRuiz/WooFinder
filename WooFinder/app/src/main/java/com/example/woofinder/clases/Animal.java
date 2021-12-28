package com.example.woofinder.clases;

import com.example.woofinder.PruebaActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class Animal {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private CollectionReference animalCollection = db.collection("Animal");
    private Map<String, Object> animal = new HashMap<>();
    private DocumentSnapshot doc;

    public Animal(String descripcion, Timestamp fecha, GeoPoint localizacion, String tipo){
        this.animal.put("descripcion", descripcion);
        this.animal.put("fecha", fecha);
        this.animal.put("localizacion", localizacion);
        this.animal.put("tipo", tipo);
    }

    // Esto hace el update y el add
    public void addAnimal(){
        animalCollection.document("prueba").set(this.animal);
    }

    public Map<String, Object> getAnimal() {
        return animal;
    }

    public void deleteAnimal(){
        //find();
        if(doc != null) doc.getReference().delete();
    }
}
