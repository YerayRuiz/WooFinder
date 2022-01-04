package com.example.woofinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.Organizacion;

import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {
    public AnimalAdapter(Context context, List<Animal> lista){
        super(context, 0, lista);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_animal, parent, false);
        }

        TextView nombreTextView = (TextView) listitemView.findViewById(R.id.animal_nombre);

        Animal animal = getItem(position);

        nombreTextView.setText(animal.getId() + ": " + animal.getDescripcion());

        return listitemView;
    }

}
