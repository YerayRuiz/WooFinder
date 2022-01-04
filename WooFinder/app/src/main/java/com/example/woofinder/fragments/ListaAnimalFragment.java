package com.example.woofinder.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.woofinder.AnimalAdapter;
import com.example.woofinder.InitialActivity;
import com.example.woofinder.OrganizacionAdapter;
import com.example.woofinder.PruebaActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaAnimalFragment extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference animalCollection;

    public ListaAnimalFragment() {
        // Required empty public constructor
    }

    public static ListaAnimalFragment newInstance() {
        ListaAnimalFragment fragment = new ListaAnimalFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        animalCollection = db.collection("Animal");

        animalCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Animal> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Animal animal = document.toObject(Animal.class);
                        animal.setId(document.getId());
                        list.add(animal);
                    }
                    ListView listaAnimales = (ListView) getView().findViewById(R.id.listaAnimales);
                    AnimalAdapter animalAdapter = new AnimalAdapter(getContext(), list);
                    listaAnimales.setAdapter(animalAdapter);

                } else {
                    Log.d("ListaAnimalFragment", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_animal, container, false);
    }
}