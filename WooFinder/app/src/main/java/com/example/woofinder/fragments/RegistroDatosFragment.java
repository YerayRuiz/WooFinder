package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.example.woofinder.clases.Solicitud;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.regex.Pattern;

public class RegistroDatosFragment extends Fragment {

    private Organizacion org;
    private TextView txtOrgNombre;
    private EditText inputEmail;
    private Button buttonSolicitar;
    private FirebaseFirestore db;
    private CollectionReference usuarioCollection;
    private CollectionReference solicitudCollection;

    public RegistroDatosFragment() {
        // Required empty public constructor
    }

    public static RegistroDatosFragment newInstance(Organizacion org) {
        RegistroDatosFragment fragment = new RegistroDatosFragment();
        Bundle args = new Bundle();
        args.putSerializable("ORGANIZACION", org);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            org = (Organizacion) getArguments().getSerializable("ORGANIZACION");
        }
        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        usuarioCollection = db.collection("Usuario");
        solicitudCollection = db.collection("Solicitud");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_datos, container, false);
        txtOrgNombre = view.findViewById(R.id.txtOrgNombre);
        txtOrgNombre.setText(org.getNombre());

        buttonSolicitar = view.findViewById(R.id.buttonSolicitar);
        inputEmail = view.findViewById(R.id.inputEmail);

        buttonSolicitar.setOnClickListener(solicitar);

        return view;
    }

    public View.OnClickListener solicitar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = inputEmail.getText().toString();
            if (!validarEmail(email)){
                inputEmail.setError(getString(R.string.email_no_encontrado));
            } else {
                usuarioCollection.whereEqualTo("correo", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> lista = task.getResult().getDocuments();
                            if (lista.size() > 0){
                                inputEmail.setError(getString(R.string.usuario_ya_existe));
                            } else {
                                solicitudCollection.whereEqualTo("correoUser", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<DocumentSnapshot> listaSol = task.getResult().getDocuments();
                                            if (listaSol.size() > 0){
                                                inputEmail.setError(getString(R.string.solicitud_ya_existe));
                                            } else {
                                                Solicitud solicitud = new Solicitud(email, org);

                                                String cadena = getString(R.string.solicitud_exito);
                                                Toast.makeText(getContext(),cadena, Toast.LENGTH_SHORT).show();

                                                //Falta hacer el intent para irse a inicio
                                                getActivity().onBackPressed();

                                                Toast.makeText(getContext(),cadena, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.d("ERROR", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        }
    };

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}