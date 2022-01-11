package com.example.woofinder.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
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
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;
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
import java.util.Locale;

public class ListaAnimalFragment extends Fragment  {

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


    private OnMapReadyCallback callback = new OnMapReadyCallback()  {
        private LatLng pnt;
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
                                CameraPosition camPos;
                                if(location != null){
                                    pnt=new LatLng(location.getLatitude(),location.getLongitude());

                                     camPos = new CameraPosition.Builder()
                                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                            .zoom(18)
                                            .bearing(location.getBearing())
                                            .tilt(70)
                                            .build();

                                }
                                else{
                                     camPos = new CameraPosition.Builder()
                                            .zoom(18)
                                            .bearing(location.getBearing())
                                            .tilt(70)
                                            .build();
                                }
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
                                                Marker m =googleMap.addMarker(marker);
                                                m.setTag(0);
                                            }

                                        } else {
                                            Log.d("ListaAnimalFragment", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                            }
                        });


            }

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Integer i = (Integer) marker.getTag();
                    System.out.println("clicks valen:"+i);
                    System.out.println("entra en onClick");
                    // Check if a click count was set, then display the click count.
                    if (i != null) {
                        System.out.println("clicks:"+i);
                        if(i > 1){
                            marker.setTag(0);

                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" +marker.getPosition().latitude+","
                                            +marker.getPosition().longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                        i = i + 1;
                        marker.setTag(i);
                    }

                    return false;
                }
            });
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {


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

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.mPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        this.mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        Toast.makeText(getActivity(),"Doble click en icono de perro para abrirlo en Maps", Toast.LENGTH_LONG).show();

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