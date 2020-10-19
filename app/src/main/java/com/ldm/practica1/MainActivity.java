package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView numeroPregunta;
    private TextView pregunta;
    private Button respuesta1;
    private Button respuesta2;
    private Button respuesta3;
    private Button respuesta4;
    private Button reiniciar;
    private Button siguiente;

    private String respuestaCorrecta;
    private int contadorPreguntas = 1;
    private int puntuacion = 0;

    ArrayList<ArrayList<String>> arrayQuiz = new ArrayList<>();
    String datosQuiz[][] = {
            {"¿Qué líder tribal luchó contra la ocupación romana de Britania?", "Boudica", "Tácito", "Ariovistus", "Prasutagus"},
            {"¿Qué emperador romano legalizó el cristianismo y puso fin a la persecución de los cristianos?", "Constantino", "Adriano", "Trajano", "Nerón"},
            {"¿Quién fue el primer presidente de los Estados Unidos?", "George Washington", "Thomas Jefferson", "Abraham Lincoln", "Andrew Jackson"},
            {"¿En qué batalla fue finalmente derrotado Napoleón Bonaparte?", "La batalla de Waterloo", "La batalla del Álamo", "La batalla de Stalingrado", "La batalla de Hastings"},
            {"¿A qué filósofo griego se le atribuye la obra \"La República\"?", "Platón", "Sócrates", "Aristóteles", "Ptolomeo"},
            {"¿Qué científico es considerado el padre de la bomba atómica?", "Robert Oppenheimer", "Albert Einstein", "Jonas Salk", "Leó Szilárd"},
            {"¿Quién fue el primer humano en viajar al espacio?", "Yuri Gagarin", "Neil Armstrong", "Buzz Aldrin", "Adriyan Nikolayev"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}