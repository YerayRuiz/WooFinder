package com.example.woofinder.clases;

import androidx.annotation.NonNull;

import com.example.woofinder.PruebaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Solicitud {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private CollectionReference solicitudCollection = db.collection("Solicitud");
    private Map<String, Object> solicitud = new HashMap<>();
    private DocumentSnapshot doc;
    public Solicitud(String cU, String cO){
        this.solicitud.put("correoUsuario",cU);
        this.solicitud.put("correoOrg",cO);
    }


    // Esto hace el update y el add
    public void addSolicitud(){
        solicitudCollection.document("prueba").set(this.solicitud);
    }
    public void deleteSolicitud(){
        findDocByCorreoUsuario();
        if(doc != null) doc.getReference().delete();
    }

    public Map<String, Object> getSolicitud() {
        return solicitud;
    }


    // Esto hay que arreglarlo
    private DocumentSnapshot findDocByCorreoUsuario(){
        Task<QuerySnapshot> q = solicitudCollection.whereEqualTo("correoUsuario", this.solicitud.get("correoUsuario")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
