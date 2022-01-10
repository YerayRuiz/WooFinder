package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.woofinder.MainActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.SingletonUsuario;
import com.example.woofinder.clases.Usuario;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarUsuarioFragment extends Fragment {
    private Usuario user;
    private TextInputEditText inputCorreo;
    private TextInputEditText inputNombre;
    private TextInputEditText inputPassword;
    private TextInputEditText inputConfirmPassword;
    private Button btnGuardarUsuario;

    public EditarUsuarioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditarUsuarioFragment newInstance() {
        EditarUsuarioFragment fragment = new EditarUsuarioFragment();

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
        View myInflatedView = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        inputCorreo = myInflatedView.findViewById(R.id.inputCorreo);
        inputNombre = myInflatedView.findViewById(R.id.inputNombre);
        inputPassword = myInflatedView.findViewById(R.id.inputPassword);
        inputConfirmPassword = myInflatedView.findViewById(R.id.inputConfirmPassword);
        btnGuardarUsuario = myInflatedView.findViewById(R.id.btnGuardarUsuario);

        inputCorreo.setText(user.getCorreo());
        inputNombre.setText(user.getNombre());
        inputPassword.setText(user.getPassword());
        inputConfirmPassword.setText(user.getPassword());
        btnGuardarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = inputPassword.getText().toString();
                String pwd2 = inputConfirmPassword.getText().toString();
                if(pwd1.equals(pwd2)) {
                    String nombre = inputNombre.getText().toString();
                    String correo = inputCorreo.getText().toString();
                    user.updateUsuario(correo, nombre, user.getOrganizacion(), pwd1);
                    ((MainActivity) getActivity()).loadFragment(new UsuarioFragment());
                    Toast.makeText(getActivity() ,"Se ha actualizado el perfil correctamente", Toast.LENGTH_LONG).show();
                } else {
                    inputPassword.setError("Las contraseñas no coinciden");
                }
            }
        });

        return myInflatedView;
    }
}