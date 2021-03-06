package com.example.woofinder.clases;

import com.example.woofinder.InitialActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Solicitud implements Serializable {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
    private CollectionReference solicitudCollection = db.collection("Solicitud");
    private CollectionReference organizacionCollection = db.collection("Organizacion");
    private String id;
    private String correoUser;
    private Organizacion organizacion;

    public Solicitud() {

    }

    public Solicitud(String correoUser, Organizacion organizacion) {
        DocumentReference organizacionReference = organizacionCollection.document(organizacion.getId());

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
        DocumentReference organizacionReference = organizacionCollection.document(organizacion.getId());
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
