package com.example.woofinder.clases;

import android.icu.lang.UScript;

import java.util.HashMap;

public class SingletonOrganizacion extends HashMap<String, Organizacion> {

    private static class SingletonHolder{
        private static final com.example.woofinder.clases.SingletonOrganizacion ourInstance = new com.example.woofinder.clases.SingletonOrganizacion();
    }

    public static com.example.woofinder.clases.SingletonOrganizacion getInstance() {
        return com.example.woofinder.clases.SingletonOrganizacion.SingletonHolder.ourInstance;
    }

    private SingletonOrganizacion(){}
}

