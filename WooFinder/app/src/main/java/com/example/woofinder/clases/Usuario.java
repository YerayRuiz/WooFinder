package com.example.woofinder.clases;

import androidx.annotation.NonNull;

import com.example.woofinder.PruebaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario implements Serializable {
    private FirebaseFirestore db= SingletonDataBase.getInstance().get(PruebaActivity.SHARED_DATA_KEY);
    private CollectionReference usuarioCollection = db.collection("Usuario");
    private CollectionReference organizacionCollection = db.collection("Organizacion");

    private String id;
    private String correo;
    private String nombre;
    private Organizacion organizacion;
    private String password;

    public Usuario() {

    }

    public Usuario(String correo, String nombre, Organizacion organizacion, String password) {
        DocumentReference organizacionReference = organizacionCollection.document(organizacion.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("correo", correo);
        data.put("nombre", nombre);
        data.put("organizacion", organizacionReference);
        data.put("password", password);

        DocumentReference newRef = db.collection("Usuario").document();
        newRef.set(data);

        this.id = newRef.getId();
        this.correo = correo;
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.password = password;
    }

    public void updateUsuario(String correo, String nombre, Organizacion organizacion, String password){
        DocumentReference organizacionReference = organizacionCollection.document(organizacion.getId());

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("correo", correo);
        usuario.put("nombre", nombre);
        usuario.put("organizacion", organizacion);
        usuario.put("password", password);

        organizacionCollection.document(this.id).set(organizacion);

        this.correo = correo;
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.password = password;
    }

    public void deleteUsuario(){
        organizacionCollection.document(this.id).delete();
        this.id = null;
        this.correo = null;
        this.nombre = null;
        this.organizacion = null;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", organizacion=" + organizacion +
                ", password='" + password + '\'' +
                '}';
    }
}
