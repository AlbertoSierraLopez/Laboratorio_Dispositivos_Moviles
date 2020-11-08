package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {

    private int contadorPreguntas = 0;
    private int puntuacion = 0;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Poner el nombre del último jugador
        SharedPreferences sp = getSharedPreferences("lastplayer", Context.MODE_PRIVATE);
        nombre = sp.getString("ultimo", "");
    }

    public int getContadorPreguntas() {
        return contadorPreguntas;
    }

    public void setContadorPreguntas(int contadorPreguntas) {
        this.contadorPreguntas = contadorPreguntas;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}