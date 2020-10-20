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
    private TextView puntuacion;
    private TextView puntuacionNumero;
    private CheckBox compartir;
    private CheckBox reiniciar;
    private CheckBox salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
    }

    public void reiniciar(View v) {
        Intent reiniciar = new Intent(this, MainActivity.class);
        startActivity(reiniciar);
    }

    public void terminar(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAndRemoveTask();
        } else {
            this.finish();
        }
        System.exit(0);
    }

    public void compartir(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
        startActivity(browserIntent);
    }
}