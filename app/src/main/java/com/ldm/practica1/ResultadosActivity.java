package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ResultadosActivity extends AppCompatActivity {
    private int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Conectar la parte lógica con el diseño
        TextView txtResultado = findViewById(R.id.txtResultado);

        // Recoge la puntuación final de la otra activity
        puntuacion = getIntent().getIntExtra("puntuacionFinal", 0);
        // Mostrar resultado final
        txtResultado.setText(String.format(Locale.getDefault(), "%d", puntuacion)); // Si no se cambia el int a string peta la app
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