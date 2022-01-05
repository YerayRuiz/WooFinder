package com.example.woofinder.clases;

import android.icu.lang.UScript;

import java.util.HashMap;

public class SingletonUsuario extends HashMap<String, Usuario> {

    private static class SingletonHolder{
        private static final com.example.woofinder.clases.SingletonUsuario ourInstance = new com.example.woofinder.clases.SingletonUsuario();
    }

    public static com.example.woofinder.clases.SingletonUsuario getInstance() {
        return com.example.woofinder.clases.SingletonUsuario.SingletonHolder.ourInstance;
    }

    private SingletonUsuario(){}
}
