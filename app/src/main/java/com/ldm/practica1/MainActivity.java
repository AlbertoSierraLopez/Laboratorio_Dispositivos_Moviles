package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // Declaración de variables
    private static final int NPREGUNTAS = 4;

    private TextView numeroPregunta;
    private TextView pregunta;
    private ListView listViewRespuestas;

    private int contadorPreguntas = 0;
    private int puntuacion = 0;

    List<String> listaRespuestas;

    private LibreriaPreguntas libreria = new LibreriaPreguntas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Conectar la parte lógica con el diseño
        numeroPregunta = findViewById(R.id.txtNumeroPregunta);
        pregunta = findViewById(R.id.txtPregunta);
        listViewRespuestas = findViewById(R.id.listViewRespuestas);
        // Lanzar el ciclo de juego que se repetirá tantas veces como preguntas haya (NPREGUNTAS)
        cicloDeJuego();
    }

    public void cicloDeJuego() {
        // Mostrar pregunta
        String sPregunta = getResources().getString(R.string.txtPregunta, contadorPreguntas + 1);   // String con valores variables que se rellenan ahora con el numero de la pregunta
        numeroPregunta.setText(sPregunta);
        pregunta.setText(libreria.getPregunta(contadorPreguntas));
        // Cargar imagen si la hubiera
        int idImagen = libreria.getImagen(contadorPreguntas);
        // idImagen puede ser una imagen guardada en R o un 0, si es un 0 se borra la imagen que hubiese de la pregunta anterior
        pregunta.setCompoundDrawablesWithIntrinsicBounds(idImagen, 0, 0, 0);
        // El texto se pega a la derecha si hay una imagen o se centra si no la hay
        if (idImagen != 0) {
            pregunta.setGravity(Gravity.LEFT);
        } else {
            pregunta.setGravity(Gravity.CENTER);
        }
        // Cargar la lista de respuestas
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(libreria.getRespuesta0(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta1(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta2(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta3(contadorPreguntas));
        // Configurar el adaptador para mostrar las respuestas
        ArrayAdapter<String> adaptadorListaRespuestas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRespuestas);
        listViewRespuestas.setAdapter(adaptadorListaRespuestas);
        listViewRespuestas.setOnItemClickListener(this);
        listViewRespuestas.setEnabled(true);    // Esto activa de nuevo el listView en caso de que hubiese sido desactivado
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        Intent intentReiniciar = new Intent(this, MainActivity.class);
        startActivity(intentReiniciar);
    }

    // La forma de avanzar a la siguiente pantalla es siempre el botón siguiente
    public void continuar(View view) {
        // El juego permite saltar preguntas sin responder
        contadorPreguntas++;    // "Al final de la pregunta el contador de preguntas señal el número real de la pregunta que se ha respondido (1-5)"
        if (contadorPreguntas < NPREGUNTAS) {   // Si aun quedan preguntas se lanza el ciclo de juego
            cicloDeJuego();
        } else {                                // Si se han respondido todas las preguntas, se lanza la pantalla resumen final
            Intent intentContinuar = new Intent(this, RespuestaFinalActivity.class);
            // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
            intentContinuar.putExtra("puntuacion", puntuacion);
            startActivity(intentContinuar);
        }
    }

    public void popupAyuda(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}