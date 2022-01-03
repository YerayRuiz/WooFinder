package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.woofinder.fragments.ListaAnimalFragment;
import com.example.woofinder.fragments.NuevoAnimalFragment;
import com.example.woofinder.fragments.UsuarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ListaAnimalFragment listaAnimalFragment = new ListaAnimalFragment();
    NuevoAnimalFragment nuevoAnimalFragment = new NuevoAnimalFragment();
    UsuarioFragment usuarioFragment = new UsuarioFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navbar);

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
                    case R.id.usuarioFragment:
                        loadFragment(usuarioFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}