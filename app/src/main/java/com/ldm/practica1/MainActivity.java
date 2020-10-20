package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView numeroPregunta;
    private TextView pregunta;
    private Button reiniciar;
    private Button siguiente;

    private int contadorPreguntas = 1;
    private int puntuacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reiniciar(View view) {
        Intent reiniciar = new Intent(this, MainActivity.class);
        startActivity(reiniciar);
    }

    /* METODO PARA DEBUG */
    public void acabar(View view) {
        Intent acabar = new Intent(this, ResultadosActivity.class);
        startActivity(acabar);
    }
}