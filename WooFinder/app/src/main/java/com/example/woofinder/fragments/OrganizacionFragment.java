package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.OrganizacionAdapter;
import com.example.woofinder.R;
import com.example.woofinder.UsuarioAdapter;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.example.woofinder.clases.SingletonOrganizacion;
import com.example.woofinder.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrganizacionFragment extends Fragment {

    private Organizacion org;
    private FirebaseFirestore db;
    private CollectionReference usuarioCollection;
    private CollectionReference organizacionCollection;

    public OrganizacionFragment() {
    }

    public static OrganizacionFragment newInstance() {
        OrganizacionFragment fragment = new OrganizacionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org = SingletonOrganizacion.getInstance().get("ORGANIZACION");

        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        usuarioCollection = db.collection("Usuario");
        organizacionCollection = db.collection("Organizacion");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_organizacion, container, false);

        TextView txtCif = (TextView) v.findViewById(R.id.txtCif);
        TextView txtCorreo = (TextView) v.findViewById(R.id.txtCorreo);
        TextView txtNombre = (TextView) v.findViewById(R.id.txtNombre);

        txtCif.setText(org.getCif());
        txtCorreo.setText(org.getCorreo());
        txtNombre.setText(org.getNombre());

        String path = organizacionCollection.document(org.getId()).getPath();

        usuarioCollection.whereEqualTo("organizacion", path)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Usuario> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> datos = document.getData();
                        Usuario usuario = new Usuario();
                        usuario.setId(document.getId());
                        usuario.setNombre(datos.get("nombre").toString());
                        usuario.setCorreo(datos.get("correo").toString());
                        usuario.setOrganizacion(org);
                        usuario.setPassword(datos.get("password").toString());
                        list.add(usuario);
                    }
                    ListView listaUsuario = (ListView) v.findViewById(R.id.listEquipo);
                    UsuarioAdapter usuarioAdapter = new UsuarioAdapter(getContext(), list);
                    listaUsuario.setAdapter(usuarioAdapter);

                } else {
                    Log.d("RegistroActivity", "Error getting documents: ", task.getException());
                }
            }
        });

        return v;
    }
}