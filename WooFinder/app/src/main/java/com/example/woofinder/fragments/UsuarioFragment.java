package com.example.woofinder.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.InicioActivity;
import com.example.woofinder.LoginActivity;
import com.example.woofinder.MainActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.SingletonUsuario;
import com.example.woofinder.clases.Usuario;

public class UsuarioFragment extends Fragment {

    private TextView viewCorreo;
    private TextView viewNombre;
    private TextView viewOrganizacion;
    private Usuario user;
    private Button btnEditarPerfil;
    private Button btnDeletePerfil;
    private Button btnCerrarSesion;

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
        btnCerrarSesion = myInflatedView.findViewById(R.id.btnCerrarSesion);

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
                builder.setMessage(getString(R.string.borrar_usuario_pregunta));

                builder.setPositiveButton(getString(R.string.btn_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.deleteUsuario();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getActivity() ,getString(R.string.usuario_eliminado), Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton(getString(R.string.btn_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle(getString(R.string.borrar_perfil));
                alert.show();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InicioActivity.class);
                startActivity(i);
            }
        });

        return myInflatedView;
    }
}