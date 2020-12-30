package com.ldm.practica3.juego;

import java.util.Random;

public class Mazmorra {
    static final int MAZMORRA_ANCHO = 10;
    static final int MAZMORRA_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Monstruos monstruos;
    public Alma alma;
    public Sacerdote sacerdote;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean campos[][] = new boolean[MAZMORRA_ANCHO][MAZMORRA_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mazmorra() {
        monstruos = new Monstruos();
        colocarAlmaYSacerdote();
    }

    private void colocarAlmaYSacerdote() {
        // Inicializar el tablero
        for (int x = 0; x < MAZMORRA_ANCHO; x++) {
            for (int y = 0; y < MAZMORRA_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = monstruos.partes.size();
        for (int i = 0; i < len; i++) {
            Esqueleto parte = monstruos.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        // Colocar Alma
        int almaX = random.nextInt(MAZMORRA_ANCHO);
        int almaY = random.nextInt(MAZMORRA_ALTO);
        while (true) {
            if (campos[almaX][almaY] == false)
                break;
            almaX += 1;
            if (almaX >= MAZMORRA_ANCHO) {
                almaX = 0;
                almaY += 1;
                if (almaY >= MAZMORRA_ALTO) {
                    almaY = 0;
                }
            }
        }
        alma = new Alma(almaX, almaY, random.nextInt(2));
        campos[almaX][almaY] = true;

        // Colocar Sacerdote
        int sacerdoteX = random.nextInt(MAZMORRA_ANCHO);
        int sacerdoteY = random.nextInt(MAZMORRA_ALTO);
        while (true) {
            if (campos[sacerdoteX][sacerdoteY] == false)
                break;
            sacerdoteX += 1;
            if (sacerdoteX >= MAZMORRA_ANCHO) {
                sacerdoteX = 0;
                sacerdoteY += 1;
                if (sacerdoteY >= MAZMORRA_ALTO) {
                    sacerdoteY = 0;
                }
            }
        }
        sacerdote = new Sacerdote(sacerdoteX, sacerdoteY, random.nextInt(3));
        campos[sacerdoteX][sacerdoteY] = true;
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;

            // Avanzar
            monstruos.avance();

            // Comprobar si el vampiro se choca con sus esqueletos
            if (monstruos.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Esqueleto vampiro = monstruos.partes.get(0);

            // Comprobar si el vampiro absorbe un alma
            if (vampiro.x == alma.x && vampiro.y == alma.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                monstruos.abordaje();
                if (monstruos.partes.size() == MAZMORRA_ANCHO * MAZMORRA_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarAlmaYSacerdote();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }

            // Comprobar si el sacerdote acaba con el vampiro
            if (vampiro.x == sacerdote.x && vampiro.y == sacerdote.y) {
                finalJuego = true;
                return;
            }
        }
    }
}

