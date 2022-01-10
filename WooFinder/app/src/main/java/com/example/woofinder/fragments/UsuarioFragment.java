package com.example.woofinder.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.LoginActivity;
import com.example.woofinder.MainActivity;
import com.example.woofinder.R;
import com.example.woofinder.RegistroActivity;
import com.example.woofinder.UsuarioActivity;
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
    private Button btnEditarPerfil;
    private Button btnDeletePerfil;

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
        btnEditarPerfil = myInflatedView.findViewById(R.id.btnEditarPerfil);
        btnDeletePerfil = myInflatedView.findViewById(R.id.btnDeletePerfil);
        viewCorreo.setText(user.getCorreo());
        viewNombre.setText(user.getNombre());
        viewOrganizacion.setText(user.getOrganizacion().getNombre());
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new EditarUsuarioFragment());
            }
        });

        btnDeletePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Desea eliminar su perfil?");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.deleteUsuario();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getActivity() ,"Se ha eliminado el perfil correctamente", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Borrar Perfil");
                alert.show();
            }
        });

        return myInflatedView;
    }
}