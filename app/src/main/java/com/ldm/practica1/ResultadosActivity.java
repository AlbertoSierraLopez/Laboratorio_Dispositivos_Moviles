package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ResultadosActivity extends AppCompatActivity {
    private TextView txtFinal;
    private TextView puntuacion;
    private TextView puntuacionNumero;
    private CheckBox compartir;
    private CheckBox reiniciar;
    private CheckBox salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
    }
}