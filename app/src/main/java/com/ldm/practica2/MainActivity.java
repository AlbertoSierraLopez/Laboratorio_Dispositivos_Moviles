package com.ldm.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {

    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear base de datos y llenarla de preguntas
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "DBPreguntas", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        // Si le das al reset se meten otra vez los mismos datos y aparecen repetidos
        // Resetear la base de datos
        db.execSQL("delete from preguntas");

        ContentValues entrada1 = new ContentValues();
        entrada1.put("codigo", 1);
        entrada1.put("pregunta", "¿Qué emperador romano legalizó el cristianismo y puso fin a la persecución de los cristianos?");
        entrada1.put("respuesta0", "Constantino");
        entrada1.put("respuesta1", "Adriano");
        entrada1.put("respuesta2", "Trajano");
        entrada1.put("respuesta3", "Nerón");
        entrada1.put("solucion", 0);
        db.insert("preguntas", null, entrada1);

        ContentValues entrada2 = new ContentValues();
        entrada2.put("codigo", 2);
        entrada2.put("pregunta", "¿A qué filósofo griego se le atribuye la obra \"La República\"?");
        entrada2.put("respuesta0", "Aristóteles");
        entrada2.put("respuesta1", "Ptolomeo");
        entrada2.put("respuesta2", "Platón");
        entrada2.put("respuesta3", "Sócrates");
        entrada2.put("solucion", 2);
        db.insert("preguntas", null, entrada2);

        ContentValues entrada3 = new ContentValues();
        entrada3.put("codigo", 3);
        entrada3.put("pregunta", "¿Cuál es el nombre del primer humano en viajar al espacio?");
        entrada3.put("respuesta0", "Buzz Aldrin");
        entrada3.put("respuesta1", "Yuri Gagarin");
        entrada3.put("respuesta2", "Neil Armstrong");
        entrada3.put("respuesta3", "Adriyan Nikolayev");
        entrada3.put("solucion", 1);
        db.insert("preguntas", null, entrada3);

        ContentValues entrada4 = new ContentValues();
        entrada4.put("codigo", 4);
        entrada4.put("pregunta", "¿En qué lugar nació Napoleón Bonaparte?");
        entrada4.put("respuesta0", "Waterloo");
        entrada4.put("respuesta1", "Milán");
        entrada4.put("respuesta2", "Marsella");
        entrada4.put("respuesta3", "Córcega");
        entrada4.put("solucion", 3);
        db.insert("preguntas", null, entrada4);

        db.close();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}