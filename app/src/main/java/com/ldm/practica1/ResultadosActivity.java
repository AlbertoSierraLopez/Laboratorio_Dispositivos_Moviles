package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ResultadosActivity extends AppCompatActivity {

    private TextView txtHighscore;
    private TextView txtResultado;

    private int puntuacion;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Conectar la parte lógica con el diseño
        txtResultado = findViewById(R.id.txtResultado);
        txtHighscore = findViewById(R.id.txtHighscore);

        // Recoge la puntuación final de la otra activity
        puntuacion = getIntent().getIntExtra("puntuacionFinal", 0);
        // Mostrar resultado final
        txtResultado.setText(String.format(Locale.getDefault(), "%d", puntuacion)); // Si no se cambia el int a string peta la app

        // Guardar el resultado en un SharedPreferences
        nombre = getIntent().getStringExtra("nombre");
        SharedPreferences sp = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        String highscore = sp.getString(nombre, "");
        if (highscore.equals("") || puntuacion > Integer.parseInt(highscore)) {
            highscore = String.valueOf(puntuacion);
            SharedPreferences.Editor obj_editor = sp.edit();
            obj_editor.putString(nombre, highscore);
            obj_editor.commit();
            txtHighscore.setText("Nuevo récord!");
        } else {
            txtHighscore.setText("Tu mejor puntuación es: " + highscore);
        }

        // Actualizar el último jugador
        SharedPreferences sp2 = getSharedPreferences("lastplayer", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor2 = sp2.edit();
        obj_editor2.putString("ultimo", nombre);
        obj_editor2.commit();
    }

    public void reiniciar(View v) {
        Intent intentReiniciar = new Intent(this, MainActivity.class);
        startActivity(intentReiniciar);
    }

    public void terminar(View v) {
        ResultadosActivity.this.finish();
        System.exit(0);
    }

    public void compartir(View v) {
        Intent intentCompartir = new Intent();
        intentCompartir.setAction(Intent.ACTION_SEND);
        intentCompartir.putExtra(Intent.EXTRA_TEXT, "He conseguido una puntuación de " + puntuacion + " en Quiz de Historia Universal.");
        intentCompartir.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intentCompartir, null);
        startActivity(shareIntent);
    }
}