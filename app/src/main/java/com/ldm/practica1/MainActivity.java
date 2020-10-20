package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // Declaración de variables
    private final int NPREGUNTAS = 5;
    private TextView numeroPregunta;
    private TextView pregunta;
    private Button reiniciar;
    private Button siguiente;
    private ListView listViewRespuestas;

    private int contadorPreguntas = 0;
    private int puntuacion = 0;

    List<String> listaRespuestas;

    private LibreriaQuiz libreria = new LibreriaQuiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectar la parte lógica con el diseño
        numeroPregunta = (TextView) findViewById(R.id.txtNumeroPregunta);
        pregunta = (TextView) findViewById(R.id.txtPregunta);
        reiniciar = (Button) findViewById(R.id.btnSiguiente);
        siguiente = (Button) findViewById(R.id.btnReiniciar);
        listViewRespuestas = (ListView) findViewById(R.id.listViewRespuestas);

        // Cargar la lista de respuestas
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(libreria.getRespuesta0(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta1(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta2(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta3(contadorPreguntas));

        // Configurar el adaptador para mostrar las respuestas
        ArrayAdapter adaptadorListaRespuestas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaRespuestas);
        listViewRespuestas.setAdapter(adaptadorListaRespuestas);
        listViewRespuestas.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String respuesta = listaRespuestas.get(position);
        if (position == libreria.getSolucion(contadorPreguntas)) {
            Toast.makeText(this, "La respuesta es correcta", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "La respuesta es incorrecta", Toast.LENGTH_LONG).show();
        }
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