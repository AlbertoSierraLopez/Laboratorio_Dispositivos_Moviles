package com.ldm.practica3.androidimpl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ldm.practica3.Audio;
import com.ldm.practica3.FileIO;
import com.ldm.practica3.Juego;
import com.ldm.practica3.Graficos;
import com.ldm.practica3.Input;
import com.ldm.practica3.Pantalla;
import com.ldm.practica3.juego.R;

public abstract class AndroidJuego extends AppCompatActivity implements Juego {
    AndroidFastRenderView renderView;
    Graficos graficos;
    Audio audio;
    Input input;
    FileIO fileIO;
    Pantalla pantalla;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graficos = new AndroidGraficos(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        pantalla = getStartScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Vampire:GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        pantalla.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        pantalla.pause();

        if (isFinishing())
            pantalla.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graficos getGraphics() {
        return graficos;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Pantalla pantalla) {
        if (pantalla == null)
            throw new IllegalArgumentException("Pantalla no debe ser null");

        this.pantalla.pause();
        this.pantalla.dispose();
        pantalla.resume();
        pantalla.update(0);
        this.pantalla = pantalla;
    }

    public Pantalla getCurrentScreen() {
        return pantalla;
    }
}