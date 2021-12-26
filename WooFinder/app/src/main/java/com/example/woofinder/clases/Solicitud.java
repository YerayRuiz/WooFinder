package com.example.woofinder.clases;

import com.example.woofinder.PruebaActivity;
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
        Task<QuerySnapshot> q = solicitudCollection.whereEqualTo("correoUsuario", "joseluischulo@gmail.com").get();
        // Se va a buscar por clave única, solo habrá una solicitud
        doc=q.getResult().getDocuments().get(0);
        return doc;
    }


}
