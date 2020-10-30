package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

public class PreguntaEspecialActivity extends AppCompatActivity {
    private static final int NPREGUNTAS = 5;

    private List<ImageView> imagenes;
    private TextView numeroPregunta;
    private TextView pregunta;

    private int puntuacion;
    private int contadorPreguntas;

    private LibreriaPreguntas libreria = new LibreriaPreguntas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_especial);

        // Conectar la parte lógica con el diseño
        numeroPregunta = findViewById(R.id.txtNumeroPregunta);
        pregunta = findViewById(R.id.txtPregunta);

        // imagenes guarda los ImageViews que componen las respuestas a la pregunta
        imagenes = new ArrayList<>();
        imagenes.add((ImageView) findViewById(R.id.img0));
        imagenes.add((ImageView) findViewById(R.id.img1));
        imagenes.add((ImageView) findViewById(R.id.img2));
        imagenes.add((ImageView) findViewById(R.id.img3));

        puntuacion = getIntent().getIntExtra("puntuacion", 0);
        contadorPreguntas = getIntent().getIntExtra("contadorPreguntas", 0);

        cicloDeJuego();
    }

    public void cicloDeJuego() {
        // Mostrar pregunta
        String sPregunta = getResources().getString(R.string.txtPregunta, contadorPreguntas + 1);   // String con valores variables que se rellenan ahora con el numero de la pregunta
        numeroPregunta.setText(sPregunta);
        // En lugar de poner el string directamente por lo visto es mejor llamarlo desde strings.xml
        pregunta.setText(libreria.getPregunta(contadorPreguntas));

        // Cargar imagen si la hubiera
        int idImagen = libreria.getImagen(contadorPreguntas);
        // idImagen puede ser una imagen guardada en R o un 0, si es un 0 se borra la imagen que hubiese de la pregunta anterior
        pregunta.setCompoundDrawablesWithIntrinsicBounds(idImagen, 0, 0, 0);
        // El texto se pega a la derecha si hay una imagen o se centra si no la hay
        if (idImagen != 0) {
            pregunta.setGravity(Gravity.START);
        } else {
            pregunta.setGravity(Gravity.CENTER);
        }

        // Mostrar respuestas
        int [] respuestas = new int[4];
        respuestas[0] = Integer.parseInt(libreria.getRespuesta0(contadorPreguntas));    // Como la dirección está guardada como un String hay que desencapsularlo antes de guardarlo
        respuestas[1] = Integer.parseInt(libreria.getRespuesta1(contadorPreguntas));
        respuestas[2] = Integer.parseInt(libreria.getRespuesta2(contadorPreguntas));
        respuestas[3] = Integer.parseInt(libreria.getRespuesta3(contadorPreguntas));

        for (int i = 0; i < 4; i++) {
            // A cada ImageView se le asigna un recurso guardado en respuestas
            imagenes.get(i).setImageResource(respuestas[i]);
        }
    }

    public void onImageViewClick(View view) {
        // pulsado es el indice del array imagenes que ocupa elementoPulsado, que luego comparamos con solucion para saber si es el correcto o no
        ImageView elementoPulsado = findViewById(view.getId());
        int indicePulsado = imagenes.indexOf(elementoPulsado);

        if (indicePulsado == libreria.getSolucion(contadorPreguntas)) {
            Toast.makeText(this, "La respuesta es correcta", Toast.LENGTH_LONG).show();
            puntuacion += 3;
        } else {
            Toast.makeText(this, "La respuesta es incorrecta", Toast.LENGTH_LONG).show();
            puntuacion -= 2;
        }

        // Las imageViews incorrectas se hacen desaparecer para revelar la correcta
        for (int i = 0; i < imagenes.size(); i++) {
            if (i != libreria.getSolucion(contadorPreguntas)) {
                imagenes.get(i).setVisibility(INVISIBLE);
            }
            // Y se elimina la posibilidad de pulsar varias veces en las respuestas para acumular puntos
            imagenes.get(i).setEnabled(false);
        }
    }

    public void reiniciar(View view) {
        Intent intentReiniciar = new Intent(this, MainActivity.class);
        startActivity(intentReiniciar);
    }

    // La forma de avanzar a la siguiente pantalla es siempre el botón siguiente
    public void continuar(View view) {
        // El juego permite saltar preguntas sin responder
        contadorPreguntas++;    // "Al final de la pregunta el contador de preguntas señal el número real de la pregunta que se ha respondido (1-5)"

        if (contadorPreguntas == NPREGUNTAS) {
            Intent intentFinalizar = new Intent(this, ResultadosActivity.class);
            // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
            intentFinalizar.putExtra("puntuacionFinal", puntuacion);
            startActivity(intentFinalizar);
        } else {
            // Si la siguiente pregunta no es especial, hay que hacer un intent y seguir en otra activity
            if (!libreria.getEspecial(contadorPreguntas)) {
                Intent intentContinuar = new Intent(this, MainActivity.class);
                // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
                intentContinuar.putExtra("puntuacion", puntuacion);
                intentContinuar.putExtra("contadorPreguntas", contadorPreguntas);
                startActivity(intentContinuar);
            } else {
                // Si no, seguimos aquí
                cicloDeJuego();
            }
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