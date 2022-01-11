package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.OrganizacionAdapter;
import com.example.woofinder.PruebaActivity;
import com.example.woofinder.R;
import com.example.woofinder.RegistroActivity;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaOrganizacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaOrganizacionesFragment extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference organizacionCollection;

    public ListaOrganizacionesFragment() {
        // Required empty public constructor
    }

    public static ListaOrganizacionesFragment newInstance() {
        ListaOrganizacionesFragment fragment = new ListaOrganizacionesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        organizacionCollection = db.collection("Organizacion");

        organizacionCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Organizacion> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> datos = document.getData();
                        Organizacion org = new Organizacion();
                        org.setId(document.getId());
                        org.setCif(datos.get("cif").toString());
                        org.setCorreo(datos.get("correo").toString());
                        org.setNombre(datos.get("nombre").toString());
                        org.setPassword(datos.get("password").toString());
                        list.add(org);
                    }
                    ListView listaOrganizaciones = (ListView) getView().findViewById(R.id.listaOrganizaciones);
                    OrganizacionAdapter organizacionAdapter = new OrganizacionAdapter(getContext(), list);
                    listaOrganizaciones.setAdapter(organizacionAdapter);

                } else {
                    Log.d("RegistroActivity", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_lista_organizaciones, container, false);
        ListView listaOrganizaciones = (ListView) vista.findViewById(R.id.listaOrganizaciones);

        listaOrganizaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Organizacion org = (Organizacion) listaOrganizaciones.getItemAtPosition(i);
                ((RegistroActivity) getActivity()).loadFragment(RegistroDatosFragment.newInstance(org));
            }
        });

        return vista;
    }
}