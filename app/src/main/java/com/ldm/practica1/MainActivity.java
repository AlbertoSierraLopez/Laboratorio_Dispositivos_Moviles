package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView numeroPregunta;
    private TextView pregunta;
    private Button reiniciar;
    private Button siguiente;
    private ListView respuestas;

    private int contadorPreguntas = 0;
    private int puntuacion = 0;

    private LibreriaQuiz libreria = new LibreriaQuiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroPregunta = (TextView) findViewById(R.id.txtNumeroPregunta);
        pregunta = (TextView) findViewById(R.id.txtPregunta);
        reiniciar = (Button) findViewById(R.id.btnSiguiente);
        siguiente = (Button) findViewById(R.id.btnReiniciar);
        respuestas = (ListView) findViewById(R.id.listViewRespuestas);

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