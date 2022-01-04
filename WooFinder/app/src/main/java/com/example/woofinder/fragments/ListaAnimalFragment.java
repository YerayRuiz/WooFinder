package com.example.woofinder.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.woofinder.AddAnimalActivity;
import com.example.woofinder.AnimalAdapter;
import com.example.woofinder.InitialActivity;
import com.example.woofinder.OrganizacionAdapter;
import com.example.woofinder.PruebaActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.Animal;
import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonDataBase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


    // Esto es para coger la ubi actual
    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if(result) {
                        System.out.println("onActivityResult: PERMISSION GRANTED");
                    } else {
                        System.out.println("onActivityResult: PERMISSION DENIED");
                    }
                }
            });


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        private LatLng pnt;
        private FusedLocationProviderClient client;

        public LatLng getpnt() {
            return this.pnt;
        }




        @Override
        public void onMapReady(GoogleMap googleMap) {


            this.client = LocationServices.getFusedLocationProviderClient(getActivity());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            else{
                System.out.println("Entra en else");
                client.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.

                                pnt=new LatLng(location.getLatitude(),location.getLongitude());

                                CameraPosition camPos = new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(18)
                                        .bearing(location.getBearing())
                                        .tilt(70)
                                        .build();
                                CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                googleMap.animateCamera(camUpd3);

                                db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
                                animalCollection = db.collection("Animal");

                                // Esto es la lista de animales
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
                                            googleMap.clear();
                                            for(Animal a: list){
                                                MarkerOptions marker = new MarkerOptions().position(new LatLng(a.getLocalizacion().getLatitude(),
                                                        a.getLocalizacion().getLongitude())).title(a.getDescripcion());
                                                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.perroicon50));
                                                googleMap.addMarker(marker);
                                            }

                                        } else {
                                            Log.d("ListaAnimalFragment", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                            }
                        });


            }

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    /*
                    pnt=point;
                    googleMap.clear();
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("New Marker");
                    googleMap.addMarker(marker);
                    AddAnimalActivity.setPoint(point);

                     */

                }
            });
        }
    };


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
        /*
        db = SingletonDataBase.getInstance().get(InitialActivity.SHARED_DATA_KEY);
        animalCollection = db.collection("Animal");

        // Esto es la lista de animales
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
         */

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.mPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        this.mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION);


        return inflater.inflate(com.example.woofinder.R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(com.example.woofinder.R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}