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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        doc = findUsuarioByCorreo();
        if(doc != null) doc.getReference().delete();
    }

    private DocumentSnapshot findUsuarioByCorreo(){
        Task<QuerySnapshot> q = usuarioCollection.whereEqualTo("correo", this.usuario.get("correo")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().getDocuments().isEmpty()){
                    doc=task.getResult().getDocuments().get(0);
                }
            }
        });
        return doc;
    }

    public List<DocumentSnapshot> getListUsuario() {
        //Esto se trae una lista de documentos
        List<DocumentSnapshot> res = new ArrayList<>();

        usuarioCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        System.out.println(document.getData());
                    }
                }
            }
        });
        return res;
    }
}
