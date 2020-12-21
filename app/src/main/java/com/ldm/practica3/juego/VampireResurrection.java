package com.ldm.practica3.juego;

import android.os.Bundle;

import com.ldm.practica3.Pantalla;
import com.ldm.practica3.androidimpl.AndroidJuego;

public class VampireResurrection extends AndroidJuego {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Modificar el Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
