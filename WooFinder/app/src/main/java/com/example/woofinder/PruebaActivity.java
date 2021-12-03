package com.example.woofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PruebaActivity extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView correoTextView;
    private FirebaseFirestore db;
    private CollectionReference crf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        enlazarControles();
    /*
        db.collection("Usuario").document("M1t0L1c1TOdRuaMngINs").get().addOnCompleteListener
                (new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                funcion(doc);
            }
        });*/
        this.crf= db.collection("Usuario");
        Task<QuerySnapshot> q = crf.whereEqualTo("correo", "joseluischulo@gmail.com").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    /*DocumentSnapshot document = task.getResult();
                      if (document.exists()){
                          System.out.println(d.getData().get("nombre"));
                      } else {
                          System.out.println("No existe el documento");
                      }
                     */
                {for (QueryDocumentSnapshot d : task.getResult()){
                    System.out.println(d.getData().get("nombre"));
                    }
                }
            }
        });

    }

    private void funcion(QueryDocumentSnapshot documentSnapshot) {
        //this.nombreTextView.setText((String) documentSnapshot.getData());
        this.correoTextView.setText((String) documentSnapshot.get("correo"));
    }

    private void enlazarControles() {
        this.db = FirebaseFirestore.getInstance();
        this.nombreTextView = findViewById(R.id.nombreTextView);
        this.correoTextView = findViewById(R.id.correoTextView);
    }
}