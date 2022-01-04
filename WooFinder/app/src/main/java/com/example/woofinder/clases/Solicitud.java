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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solicitud implements Serializable {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private CollectionReference solicitudCollection = db.collection("Solicitud");
    private String id;
    private String correoUser;
    private Organizacion organizacion;

    public Solicitud() {

    }

    public Solicitud(String correoUser, Organizacion organizacion) {
        DocumentReference organizacionReference = db.document(organizacion.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("correoUser", correoUser);
        data.put("idOrganizacion", organizacionReference);

        DocumentReference newRef = db.collection("Solicitud").document();
        newRef.set(data);

        this.id = newRef.getId();
        this.correoUser = correoUser;
        this.organizacion = organizacion;
    }

    public void updateSolicitud(String correoUser, Organizacion organizacion){
        DocumentReference organizacionReference = db.document(organizacion.getId());
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("correoUser", correoUser);
        solicitud.put("idOrganizacion", organizacionReference);

        solicitudCollection.document(this.id).set(solicitud);

        this.correoUser = correoUser;
        this.organizacion = organizacion;
    }

    public void deleteSolicitud(){
        solicitudCollection.document(this.id).delete();
        this.id = null;
        this.correoUser = null;
        this.organizacion = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id='" + id + '\'' +
                ", correoUser='" + correoUser + '\'' +
                ", organizacion=" + organizacion +
                '}';
    }
}
