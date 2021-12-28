package com.example.woofinder.clases;

import androidx.annotation.NonNull;

import com.example.woofinder.PruebaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

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
        findAnimalByDescripcion();
        if(doc != null) doc.getReference().delete();
    }

    private DocumentSnapshot findAnimalByDescripcion() {
        Task<QuerySnapshot> q = animalCollection.whereEqualTo("descripcion", this.animal.get("descripcion")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().getDocuments().isEmpty()){
                    doc=task.getResult().getDocuments().get(0);
                }
            }
        });
        return doc;
    }
}
