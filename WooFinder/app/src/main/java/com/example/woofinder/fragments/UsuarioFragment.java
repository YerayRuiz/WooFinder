package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.SingletonDataBase;
import com.example.woofinder.clases.SingletonUsuario;
import com.example.woofinder.clases.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsuarioFragment extends Fragment {

    private TextView viewCorreo;
    private TextView viewNombre;
    private TextView viewOrganizacion;
    private Usuario user;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    public static UsuarioFragment newInstance(String param1, String param2) {
        UsuarioFragment fragment = new UsuarioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SingletonUsuario.getInstance().get("USUARIO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_usuario, container, false);
        viewCorreo = myInflatedView.findViewById(R.id.textCorreo);
        viewNombre = myInflatedView.findViewById(R.id.textNombre);
        viewOrganizacion = myInflatedView.findViewById(R.id.textOrganizacion);
        viewCorreo.setText(user.getCorreo());
        viewNombre.setText(user.getNombre());
        viewOrganizacion.setText(user.getOrganizacion().getNombre());
        return myInflatedView;
    }
}