package com.example.woofinder.clases;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SingletonDataBase {
    private static FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static CollectionReference usuarioCollection= db.collection("Usuario");
    private static CollectionReference animalCollection= db.collection("Animal");
    private static CollectionReference organizacionCollection= db.collection("Organizacion");
    private static CollectionReference solicitudCollection= db.collection("Solicitud");




}
