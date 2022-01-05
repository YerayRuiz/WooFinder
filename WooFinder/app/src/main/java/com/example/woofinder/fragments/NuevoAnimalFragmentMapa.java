package com.example.woofinder.fragments;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woofinder.InitialActivity;
import com.example.woofinder.R;
import com.example.woofinder.clases.SingletonDataBase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NuevoAnimalFragmentMapa extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference animalCollection;


    // Esto es para coger la ubi actual
    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        System.out.println("onActivityResult: PERMISSION GRANTED");
                    } else {
                        System.out.println("onActivityResult: PERMISSION DENIED");
                    }
                }
            });


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        public LatLng pnt;
        private FusedLocationProviderClient client;


        public LatLng getpnt() {
            return this.pnt;
        }


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LocationRequest mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(60000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationCallback mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            //TODO: UI updates.
                        }
                    }
                }
            };
            /*
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                retur
             */

            //this.client = LocationServices.getFusedLocationProviderClient(getActivity());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            else{
                LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                System.out.println("Entra en else");

                LocationServices.getFusedLocationProviderClient(getActivity()).
                        getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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
/*
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

 */

                    }
                });


            }

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {

                    pnt=point;
                    googleMap.clear();
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("New Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.perroicon50));
                    googleMap.addMarker(marker);
                    //AddAnimalActivity.setPoint(point);


                }
            });
        }
    };


    public NuevoAnimalFragmentMapa() {
        // Required empty public constructor
    }

    public static NuevoAnimalFragmentMapa newInstance() {
        NuevoAnimalFragmentMapa fragment = new NuevoAnimalFragmentMapa();

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


        return inflater.inflate(R.layout.fragment_nuevo_animal_mapa, container, false);
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