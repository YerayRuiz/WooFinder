package com.example.woofinder.clases;



import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class SingletonPoint extends HashMap<String, LatLng> {
    private static class SingletonHolder{
        private static final com.example.woofinder.clases.SingletonPoint ourInstance = new com.example.woofinder.clases.SingletonPoint();
    }

    public static com.example.woofinder.clases.SingletonPoint getInstance() {
        return com.example.woofinder.clases.SingletonPoint.SingletonHolder.ourInstance;
    }

    private SingletonPoint(){}
}
