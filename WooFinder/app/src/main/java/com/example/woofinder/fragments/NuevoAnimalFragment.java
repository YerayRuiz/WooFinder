package com.example.woofinder.fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.woofinder.MainActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.SingletonPoint;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

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

    public String getTxtNombreAnimalString() {
        return txtNombreAnimal.getText().toString();
    }

    public String getTxtTipoAnimalString() {
        return txtTipoAnimal.getText().toString();
    }

    public Button getBtnNuevoAnimal() {
        return btnNuevoAnimal;
    }

    public EditText getTxtTipoAnimal() {
        return txtTipoAnimal;
    }

    public EditText getTxtNombreAnimal() {
        return txtNombreAnimal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.fragment_nuevo_animal, container, false);

        this.btnNuevoAnimal=myInflatedView.findViewById(R.id.btnNuevoAnimal);
        this.txtNombreAnimal = myInflatedView.findViewById(R.id.txtnameAnimal);
        this.txtTipoAnimal = myInflatedView.findViewById(R.id.txtTypeAnimal);

        this.btnNuevoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng point = SingletonPoint.getInstance().get("POINT");
                if(point == null){
                    Toast.makeText(getActivity(),
                            "Click en el mapa por favor", Toast.LENGTH_LONG).show();
                }else{
                    Timestamp t = new Timestamp(new Date());
                    GeoPoint g = new GeoPoint(point.latitude, point.longitude);
                    Animal a = new Animal(getTxtNombreAnimalString(),t, g, getTxtTipoAnimalString());
                    Toast.makeText(getActivity(),
                            "Animal creado exitosamente", Toast.LENGTH_LONG).show();

                    if(getActivity().getClass().equals(MainActivity.class))
                    ((MainActivity) getActivity()).loadFragment(new ListaAnimalFragment());
                }

            }
        });
        return myInflatedView;
    }
}