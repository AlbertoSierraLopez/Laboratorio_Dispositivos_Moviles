package com.ldm.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ResultadosActivity extends AppCompatActivity {
    private TextView txtFinal;
    private TextView txtPuntuacion;
    private TextView txtResultado;
    private CheckBox compartir;
    private CheckBox reiniciar;
    private CheckBox salir;

    private int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Conectar la parte lógica con el diseño
        txtResultado = (TextView) findViewById(R.id.txtResultado);

        // Recoge la puntuación final de la otra activity
        puntuacion = getIntent().getIntExtra("puntuacionFinal", 0);
        // Mostrar resultado final
        txtResultado.setText(Integer.toString(puntuacion)); // Sin el toString peta la aplicación
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