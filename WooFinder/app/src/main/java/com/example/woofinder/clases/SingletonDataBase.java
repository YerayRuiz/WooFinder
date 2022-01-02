package com.example.woofinder.clases;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SingletonDataBase extends HashMap<String,FirebaseFirestore> {

    private static class SingletonHolder{
        private static final SingletonDataBase ourInstance = new SingletonDataBase();
    }
    public static SingletonDataBase getInstance() {
        return SingletonHolder.ourInstance;
    }

    private SingletonDataBase(){}
}
