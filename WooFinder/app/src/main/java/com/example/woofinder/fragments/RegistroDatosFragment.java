package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.R;
import com.example.woofinder.clases.Organizacion;

import java.util.regex.Pattern;

public class RegistroDatosFragment extends Fragment {

    private Organizacion org;
    private TextView txtOrgNombre;
    private EditText inputEmail;
    private Button buttonSolicitar;

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
                inputEmail.setError("Email no válido");
            } else {
                String cadena = "Se creará una solicitud de registro para el email "+email+
                        " en la organizacion" + org.getNombre();
                Toast.makeText(getContext(),cadena, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}