package com.example.woofinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public UsuarioAdapter(Context context, List<Usuario> lista){
        super(context, 0, lista);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuario, parent, false);
        }

        TextView nombreTextView = (TextView) listitemView.findViewById(R.id.usuario_nombre);
        TextView correoTextView = (TextView) listitemView.findViewById(R.id.usuario_correo);

        Usuario usuario = getItem(position);

        nombreTextView.setText(usuario.getNombre());
        correoTextView.setText(usuario.getCorreo());

        return listitemView;
    }
}
