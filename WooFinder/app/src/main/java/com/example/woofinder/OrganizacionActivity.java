package com.example.woofinder;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.woofinder.fragments.ListaAnimalFragment;
import com.example.woofinder.fragments.NuevoAnimalFragment;
import com.example.woofinder.fragments.OrganizacionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OrganizacionActivity extends AppCompatActivity {

    ListaAnimalFragment listaAnimalFragment = new ListaAnimalFragment();
    NuevoAnimalFragment nuevoAnimalFragment = new NuevoAnimalFragment();
    OrganizacionFragment organizacionFragment = new OrganizacionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizacion);

        BottomNavigationView navigation = findViewById(R.id.navbarOrganizacion);

        //Esto lo hago para que se cargue por defecto el de la lista de animales
        loadFragment(listaAnimalFragment);

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.listaAnimalFragment:
                        loadFragment(listaAnimalFragment);
                        return true;
                    case R.id.nuevoAnimalFragment:
                        loadFragment(nuevoAnimalFragment);
                        return true;
                    case R.id.organizacionFragment:
                        loadFragment(organizacionFragment);
                        return true;
                }
                return false;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                loadFragment(organizacionFragment);
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameOrganizacion, fragment);
        transaction.commit();
    }
}