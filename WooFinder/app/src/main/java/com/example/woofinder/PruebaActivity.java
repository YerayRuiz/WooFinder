package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PruebaActivity extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView correoTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        enlazarControles();

        db.collection("Usuario").document("M1t0L1c1TOdRuaMngINs").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                funcion(doc);
            }
        });
    }

    private void funcion(DocumentSnapshot documentSnapshot) {
        this.nombreTextView.setText((String) documentSnapshot.get("nombre"));
        this.correoTextView.setText((String) documentSnapshot.get("correo"));
    }

    private void enlazarControles() {
        this.db = FirebaseFirestore.getInstance();
        this.nombreTextView = findViewById(R.id.nombreTextView);
        this.correoTextView = findViewById(R.id.correoTextView);
    }
}