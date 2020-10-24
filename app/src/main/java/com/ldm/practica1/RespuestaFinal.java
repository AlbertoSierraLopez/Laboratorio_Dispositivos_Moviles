package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RespuestaFinal extends AppCompatActivity {
    // Declaración de variables
    private TextView numeroPregunta;
    private TextView pregunta;
    private Button reiniciar;
    private Button siguiente;
    private List<ImageView> imagenes;

    private int puntuacion;

    String preguntaFinal = "¿Qué escudo de armas representa al antiguo Sacro Imperio Romano Germánico?";
    int[] respuestas = {R.drawable.lesser_arms_of_bohemia_and_moravia__1939_1945__svg, R.drawable.holy_roman_empire_arms_double_head_256px_svg, R.drawable.coat_of_arms_holy_see_svg, R.drawable.coat_of_arms_of_the_house_of_savoy_198px_svg};
    int solucion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_final);

        // Conectar la parte lógica con el diseño
        numeroPregunta = (TextView) findViewById(R.id.txtNumeroPregunta);
        pregunta = (TextView) findViewById(R.id.txtPregunta);
        reiniciar = (Button) findViewById(R.id.btnSiguiente);
        siguiente = (Button) findViewById(R.id.btnReiniciar);

        imagenes = new ArrayList<>();
        imagenes.add((ImageView) findViewById(R.id.img0));
        imagenes.add((ImageView) findViewById(R.id.img1));
        imagenes.add((ImageView) findViewById(R.id.img2));
        imagenes.add((ImageView) findViewById(R.id.img3));

        puntuacion = getIntent().getIntExtra("puntuacion", 0);

        // Mostrar pregunta
        numeroPregunta.setText("Pregunta final");
        pregunta.setText(preguntaFinal);

        // Mostrar respuestas
        for (int i = 0; i < 4; i++) {
            imagenes.get(i).setImageResource(respuestas[i]);
        }
    }

    public void onClick(View view) {

    }

    public void reiniciar(View view) {
        Intent reiniciar = new Intent(this, MainActivity.class);
        startActivity(reiniciar);
    }

    public void continuar(View view) {
        Intent acabar = new Intent(this, ResultadosActivity.class);
        // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
        acabar.putExtra("puntuacionFinal", puntuacion);
        startActivity(acabar);
    }
}