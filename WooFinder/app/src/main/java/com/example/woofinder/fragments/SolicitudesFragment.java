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
import com.example.woofinder.R;
import com.example.woofinder.SolicitudAdapter;
import com.example.woofinder.UsuarioAdapter;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.example.woofinder.clases.SingletonOrganizacion;
import com.example.woofinder.clases.Solicitud;
import com.example.woofinder.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitudesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitudesFragment extends Fragment {

    private Organizacion org;
    private FirebaseFirestore db;
    private CollectionReference solicitudCollection;
    private CollectionReference organizacionCollection;

    public SolicitudesFragment() {
        // Required empty public constructor
    }

    public static SolicitudesFragment newInstance() {
        SolicitudesFragment fragment = new SolicitudesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org = SingletonOrganizacion.getInstance().get("ORGANIZACION");

        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        solicitudCollection = db.collection("Solicitud");
        organizacionCollection = db.collection("Organizacion");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_solicitudes, container, false);

        TextView orgNombre = v.findViewById(R.id.txtOrganizacionNombre);
        orgNombre.setText(org.getNombre());

        DocumentReference ref = organizacionCollection.document(org.getId());

        solicitudCollection.whereEqualTo("idOrganizacion", ref)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Solicitud> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> datos = document.getData();
                        System.out.println(datos);
                        Solicitud solicitud = new Solicitud();
                        solicitud.setId(document.getId());
                        solicitud.setOrganizacion(org);
                        solicitud.setCorreoUser(datos.get("correoUser").toString());
                        list.add(solicitud);
                    }
                    ListView listaUsuario = (ListView) v.findViewById(R.id.listaSolicitudes);
                    SolicitudAdapter solicitudAdapter = new SolicitudAdapter(getContext(), list);
                    listaUsuario.setAdapter(solicitudAdapter);

                } else {
                    Log.d("SolicitudesFragment", "Error getting documents: ", task.getException());
                }
            }
        });

        return v;
    }
}