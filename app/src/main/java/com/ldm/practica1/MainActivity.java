package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

        cicloDeJuego();
    }

    public void cicloDeJuego() {
        // Mostrar pregunta
        numeroPregunta.setText("Pregunta " + (contadorPreguntas + 1));
        pregunta.setText(libreria.getPregunta(contadorPreguntas));

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
        listViewRespuestas.setEnabled(true);    // Esto activa de nuevo el listView en caso de que hubiese sido desactivado
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String respuesta = listaRespuestas.get(position);

        if (position == libreria.getSolucion(contadorPreguntas)) {
            Toast.makeText(this, "La respuesta es correcta", Toast.LENGTH_LONG).show();
            puntuacion += 3;
        } else {
            Toast.makeText(this, "La respuesta es incorrecta", Toast.LENGTH_LONG).show();
            puntuacion -= 2;
        }

        // Cambiar los colores de las respuestas para revelar la correcta
        for (int i = 0; i < listViewRespuestas.getChildCount(); i++) {
            if (i == libreria.getSolucion(contadorPreguntas)) {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#A2D2FF"));
            } else {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#FFAFCC"));
            }
        }

        // Solo se puede responder una vez
        listViewRespuestas.setEnabled(false);   // Desactiva el listView para no responder múltiples veces a la misma pregunta y ganar puntos extra
    }

    public void reiniciar(View view) {
        Intent reiniciar = new Intent(this, MainActivity.class);
        startActivity(reiniciar);
    }

    public void continuar(View view) {
        // El juego permite saltar preguntas sin responder
        contadorPreguntas++;    // "Al final de la pregunta el contador de preguntas señal el número real de la pregunta que se ha respondido (1-5)"
        if (contadorPreguntas < NPREGUNTAS) {   // Si aun quedan preguntas se lanza el ciclo de juego
            cicloDeJuego();
        } else {                                // Si se han respondido todas las preguntas, se lanza la pantalla resumen final
            Intent acabar = new Intent(this, ResultadosActivity.class);
            // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
            acabar.putExtra("puntuacionFinal", puntuacion);
            startActivity(acabar);
        }
    }

    /* METODO PARA DEBUG */
    public void acabar(View view) {
        Intent acabar = new Intent(this, ResultadosActivity.class);
        startActivity(acabar);
    }
}