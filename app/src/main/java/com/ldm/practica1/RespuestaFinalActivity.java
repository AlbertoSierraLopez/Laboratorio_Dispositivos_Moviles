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

public class RespuestaFinalActivity extends AppCompatActivity {
    private List<ImageView> imagenes;

    private int puntuacion;

    // Esto se puede meter en LibreriaQuiz, pero como solo hay una pregunta de este tipo no me he molestado
    String preguntaFinal = "¿Qué escudo de armas representa al antiguo Sacro Imperio Romano Germánico?";
    // respuestas es un array de imágenes, o mejor dicho de direcciones de memoria con esas imágenes
    int[] respuestas = {R.drawable.escudo_bohemia, R.drawable.escudo_sacro_imperio, R.drawable.escudo_papal, R.drawable.escudo_savoya};
    // solucion es el indice de respuestas que corresponde con la respuesta correcta
    int solucion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_final);

        // Conectar la parte lógica con el diseño
        // Declaración de variables
        TextView numeroPregunta = findViewById(R.id.txtNumeroPregunta);
        TextView pregunta = findViewById(R.id.txtPregunta);

        // imagenes guarda los ImageViews que componen las respuestas a la pregunta
        imagenes = new ArrayList<>();
        imagenes.add((ImageView) findViewById(R.id.img0));
        imagenes.add((ImageView) findViewById(R.id.img1));
        imagenes.add((ImageView) findViewById(R.id.img2));
        imagenes.add((ImageView) findViewById(R.id.img3));

        puntuacion = getIntent().getIntExtra("puntuacion", 0);

        // Mostrar pregunta
        numeroPregunta.setText(R.string.txtPreguntaFinal);
        // En lugar de poner el string directamente por lo visto es mejor llamarlo desde strings.xml
        pregunta.setText(preguntaFinal);
        // Mostrar respuestas
        for (int i = 0; i < 4; i++) {
            // A cada ImageView se le asigna un recurso guardado en respuestas
            imagenes.get(i).setImageResource(respuestas[i]);
        }
    }

    public void onImageViewClick(View view) {
        // pulsado es el indice del array imagenes que ocupa elementoPulsado, que luego comparamos con solucion para saber si es el correcto o no
        ImageView elementoPulsado = findViewById(view.getId());
        int indicePulsado = imagenes.indexOf(elementoPulsado);
        if (indicePulsado == solucion) {
            Toast.makeText(this, "La respuesta es correcta", Toast.LENGTH_LONG).show();
            // La pregunta final es especial y suma el doble de puntos
            puntuacion += 6;
        } else {
            Toast.makeText(this, "La respuesta es incorrecta", Toast.LENGTH_LONG).show();
            puntuacion -= 2;
        }
        // Las imageViews incorrectas se hacen desaparecer para revelar la correcta
        for (int i = 0; i < imagenes.size(); i++) {
            if (i != solucion) {
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
        Intent intentContinuar = new Intent(this, ResultadosActivity.class);
        // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
        intentContinuar.putExtra("puntuacionFinal", puntuacion);
        startActivity(intentContinuar);
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