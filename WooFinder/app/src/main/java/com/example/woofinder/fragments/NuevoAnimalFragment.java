package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.woofinder.R;
import com.example.woofinder.clases.Animal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevoAnimalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevoAnimalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button btnNuevoAnimal;
    private EditText txtNombreAnimal;
    private EditText txtTipoAnimal;
    public NuevoAnimalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevoAnimalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevoAnimalFragment newInstance(String param1, String param2) {
        NuevoAnimalFragment fragment = new NuevoAnimalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //enlazarControles();
    }

    private void enlazarControles() {
        this.btnNuevoAnimal = getView().findViewById(R.id.btnNuevoAnimal);
        this.txtNombreAnimal = getView().findViewById(R.id.txtDescripcionAnimal);
        this.txtTipoAnimal = getView().findViewById(R.id.txtTipoAnimal);

        this.btnNuevoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animal a = new Animal()
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuevo_animal, container, false);
    }
}