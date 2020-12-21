package com.ldm.practica3.juego;

import java.util.List;

import android.graphics.Color;

import com.ldm.practica3.Juego;
import com.ldm.practica3.Graficos;
import com.ldm.practica3.Input.TouchEvent;
import com.ldm.practica3.Pixmap;
import com.ldm.practica3.Pantalla;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mazmorra mazmorra;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";

    public PantallaJuego(Juego juego) {
        super(juego);
        mazmorra = new Mazmorra();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if (estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if (estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if (estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if (estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0)
            estado = EstadoJuego.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        // Suena la música
        if (Configuraciones.sonidoHabilitado) {
            Assets.ambiente.play();
            Assets.ambiente.setLooping(true);
        }

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y < 106) {
                    if (Configuraciones.sonidoHabilitado)
                        Assets.cursor.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (event.x < 64 && event.y > 416) {
                    mazmorra.monstruos.girarIzquierda();
                }
                if (event.x > 256 && event.y > 416) {
                    mazmorra.monstruos.girarDerecha();
                }
            }
        }

        mazmorra.update(deltaTime);
        if (mazmorra.finalJuego) {
            if (Configuraciones.sonidoHabilitado)
                Assets.derrota.play(1);
            estado = EstadoJuego.FinJuego;
        }
        if (antiguaPuntuacion != mazmorra.puntuacion) {
            antiguaPuntuacion = mazmorra.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if (Configuraciones.sonidoHabilitado)
                Assets.magia.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        // Para la música
        if (Configuraciones.sonidoHabilitado && Assets.ambiente.isPlaying()) {
            Assets.ambiente.pause();
        }

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 72 && event.x <= 240) {
                    if (event.y > 146 && event.y < 170) {
                        if (Configuraciones.sonidoHabilitado) {
                            Assets.cursor.play(1);
                        }
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if (event.y > 180 && event.y < 205) {
                        if (Configuraciones.sonidoHabilitado) {
                            Assets.cursor.play(1);
                            // Fuera la música
                            Assets.ambiente.stop();
                        }
                        juego.setScreen(new MainMenuScreen(juego));

                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        // Fuera la música
        if (Configuraciones.sonidoHabilitado) {
            Assets.ambiente.stop();
        }

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if (Configuraciones.sonidoHabilitado)
                        Assets.cursor.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }


    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        drawWorld(mazmorra);
        if (estado == EstadoJuego.Preparado)
            drawReadyUI();
        if (estado == EstadoJuego.Ejecutandose)
            drawRunningUI();
        if (estado == EstadoJuego.Pausado)
            drawPausedUI();
        if (estado == EstadoJuego.FinJuego)
            drawGameOverUI();


        drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2, g.getHeight() - 42);
    }

    private void drawWorld(Mazmorra mazmorra) {
        Graficos g = juego.getGraphics();
        Monstruos monstruos = mazmorra.monstruos;
        Esqueleto head = monstruos.partes.get(0);
        Alma alma = mazmorra.alma;


        Pixmap stainPixmapAlma = null;
        if (alma.tipo== Alma.TIPO_1)
            stainPixmapAlma = Assets.alma1;
        if (alma.tipo == Alma.TIPO_2)
            stainPixmapAlma = Assets.alma2;
        int x = alma.x * 32;
        int y = alma.y * 32;
        g.drawPixmap(stainPixmapAlma, x, y);

        int len = monstruos.partes.size();
        for (int i = 1; i < len; i++) {
            Pixmap stainPixmapEsqueleto = null;
            Esqueleto part = monstruos.partes.get(i);
            if (part.tipo == Esqueleto.TIPO_1)
                stainPixmapEsqueleto = Assets.esqueleto1;
            if (part.tipo == Esqueleto.TIPO_2)
                stainPixmapEsqueleto = Assets.esqueleto2;
            if (part.tipo == Esqueleto.TIPO_3)
                stainPixmapEsqueleto = Assets.esqueleto3;
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(stainPixmapEsqueleto, x, y);
        }

        Pixmap headPixmap = null;
        if (monstruos.direccion == Monstruos.ARRIBA)
            headPixmap = Assets.vampiroarriba;
        if (monstruos.direccion == Monstruos.IZQUIERDA)
            headPixmap = Assets.vampiroizquierda;
        if (monstruos.direccion == Monstruos.ABAJO)
            headPixmap = Assets.vampiroabajo;
        if (monstruos.direccion == Monstruos.DERECHA)
            headPixmap = Assets.vampiroderecha;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.botones, 0, 0, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.finjuego, 62, 100);
        g.drawPixmap(Assets.botones, 128, 200, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if (estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        if (mazmorra.finalJuego) {
            Configuraciones.addScore(mazmorra.puntuacion);
            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}