package com.ldm.practica2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ldm.practica2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity  {

    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Personalizar action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // Copiar el fichero db al almacenamiento interno del telefono
        // En teoría este proceso sólo sería necesario la primera vez que ejecutamos la aplicación:
        String appDataPath = getApplicationInfo().dataDir;

        // Asegurar que el directorio /databases existe en el telefono
        File dbFolder = new File(appDataPath + "/databases");
        dbFolder.mkdir();

        // Crear archivo en el telefono
        File dbFilePath = new File(appDataPath + "/databases/DBPreguntas.db");

        // Volcar contenido de la base de datos al telefono
        try {
            InputStream inputStream = getAssets().open("DBPreguntas.db");
            OutputStream outputStream = new FileOutputStream(dbFilePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}