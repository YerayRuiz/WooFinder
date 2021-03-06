package com.example.woofinder.clases;

import com.example.woofinder.InitialActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Organizacion implements Serializable {
    private FirebaseFirestore db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
    private CollectionReference organizacionCollection = db.collection("Organizacion");
    private String id;
    private String cif;
    private String correo;
    private String nombre;
    private String password;

    public Organizacion(){
    }

    public Organizacion(String cif, String correo, String nombre, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("cif", cif);
        data.put("correo", correo);
        data.put("nombre", nombre);
        data.put("password", password);

        DocumentReference newRef = db.collection("Organizacion").document();
        newRef.set(data);

        this.id = newRef.getId();
        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getCif() {
        return cif;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    @Override
    public String toString() {
        return "Organizacion{" +
                "id='" + id + '\'' +
                ", cif='" + cif + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
    */

    public void updateOrganizacion(String cif, String correo, String nombre, String password){
        Map<String, Object> organizacion = new HashMap<>();
        organizacion.put("cif", cif);
        organizacion.put("correo", correo);
        organizacion.put("nombre", nombre);
        organizacion.put("password", password);

        organizacionCollection.document(this.id).set(organizacion);

        this.cif = cif;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public void deleteOrganizacion(){
        organizacionCollection.document(this.id).delete();
        this.id = null;
        this.cif = null;
        this.correo = null;
        this.nombre = null;
        this.password = null;
    }

    @Override
    public String toString() {
        return "Organizacion{" +
                "id='" + id + '\'' +
                ", cif='" + cif + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
