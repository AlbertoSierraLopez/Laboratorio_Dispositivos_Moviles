package com.ldm.practica3.juego;

import com.ldm.practica3.Pantalla;
import com.ldm.practica3.androidimpl.AndroidJuego;

public class VampireResurrection extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
