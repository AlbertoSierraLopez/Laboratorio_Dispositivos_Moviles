package com.ldm.practica2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.ldm.practica2.Constants.Constants;
import com.ldm.practica2.Database.AdminSQLiteOpenHelper;
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

        // Copiar el fichero db al almacenamiento interno del telefono
        String appDataPath = getApplicationInfo().dataDir;

        // Asegurar que el directorio /databases existe
        File dbFolder = new File(appDataPath + "/databases");
        dbFolder.mkdir();

        // Crear archivo
        File dbFilePath = new File(appDataPath + "/databases/DBPreguntas.db");

        // Volcar contenido del archivo en assets al telefono
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