package com.example.woofinder.clases;

import com.example.woofinder.PruebaActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private CollectionReference usuarioCollection = db.collection("Usuario");
    private Map<String, Object> usuario = new HashMap<>();
    private DocumentSnapshot doc;

    public Usuario(String correo, String nombre, String organizacion, String password){
        this.usuario.put("correo", correo);
        this.usuario.put("nombre", nombre);
        this.usuario.put("organizacion", organizacion);
        this.usuario.put("password", password);
    }

    // Esto hace el update y el add
    public void addUsuario(){
        usuarioCollection.document("prueba").set(this.usuario);
    }

    public Map<String, Object> getUsuario() {
        return usuario;
    }

    public void deleteUsuario(){
        //find();
        if(doc != null) doc.getReference().delete();
    }
}
