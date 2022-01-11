package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.woofinder.OrganizacionActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonOrganizacion;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarOrganizacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarOrganizacionFragment extends Fragment {

    Organizacion org;
    EditText editCif, editNombre, editCorreo, editPassword, editConfirmPassword;
    Button btnActualizar;

    public EditarOrganizacionFragment() {
    }

    public static EditarOrganizacionFragment newInstance() {
        EditarOrganizacionFragment fragment = new EditarOrganizacionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        org = SingletonOrganizacion.getInstance().get("ORGANIZACION");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_organizacion, container, false);

        editCif = v.findViewById(R.id.editTextCif);
        editCorreo = v.findViewById(R.id.editTextCorreo);
        editNombre = v.findViewById(R.id.editTextNombre);
        btnActualizar = v.findViewById(R.id.btnActualizarOrg);
        editPassword = v.findViewById(R.id.editTextPassword);
        editConfirmPassword = v.findViewById(R.id.editTextConfirmPassword);

        editCif.setText(org.getCif());
        editNombre.setText(org.getNombre());
        editCorreo.setText(org.getCorreo());

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cif = editCif.getText().toString();
                String correo = editCorreo.getText().toString();
                String nombre = editNombre.getText().toString();
                String password = editPassword.getText().toString();
                String confirmpassword = editConfirmPassword.getText().toString();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                boolean emailvalido = pattern.matcher(correo).matches();

                if (!cif.equals("")){
                    if (emailvalido){
                        if (!nombre.equals("")){
                            if (!password.equals("")){
                                if (password.equals(confirmpassword)){
                                    org.updateOrganizacion(cif, correo, nombre, password);
                                    SingletonOrganizacion.getInstance().put("ORGANIZACION", org);
                                    ((OrganizacionActivity) getActivity()).loadFragment(OrganizacionFragment.newInstance());
                                } else {
                                    editConfirmPassword.setError(getString(R.string.password_no_coinciden));
                                }
                            } else {
                                editPassword.setError(getString(R.string.password_vacia));
                            }
                        } else {
                            editNombre.setError(getString(R.string.nombre_vacia));
                        }
                    } else {
                        editCorreo.setError(getString(R.string.email_no_valido));
                    }
                } else {
                    editCif.setError(getString(R.string.cif_no_valido));
                }
            }
        });

        return v;
    }
}