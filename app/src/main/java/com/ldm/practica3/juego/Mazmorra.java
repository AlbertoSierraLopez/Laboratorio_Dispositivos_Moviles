package com.ldm.practica3.juego;

import java.util.Random;

import static java.lang.Math.abs;

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

        // Inicializar el tablero a false en todas las casillas
        for (int x = 0; x < MAZMORRA_ANCHO; x++) {
            for (int y = 0; y < MAZMORRA_ALTO; y++) {
                campos[x][y] = false;
            }
        }
        // Poner las casillas de los esqueletos a true
        int len = monstruos.partes.size();
        for (int i = 0; i < len; i++) {
            Esqueleto parte = monstruos.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        colocarAlmaYSacerdote();
    }

    private void colocarAlmaYSacerdote() {
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
        Esqueleto vampiro = monstruos.partes.get(0);
        int i = 0;
        while (i < MAZMORRA_ANCHO * MAZMORRA_ALTO) {    // No probar eternamente
            if (campos[sacerdoteX][sacerdoteY] == false && !cerca(sacerdoteX, sacerdoteY, vampiro))    // Los sacerdotes no aparecen justo al lado del vampiro para evitar injusticias
                break;
            sacerdoteX += 1;
            if (sacerdoteX >= MAZMORRA_ANCHO) {
                sacerdoteX = 0;
                sacerdoteY += 1;
                if (sacerdoteY >= MAZMORRA_ALTO) {
                    sacerdoteY = 0;
                }
            }
            i++;
        }
        if (i == MAZMORRA_ANCHO * MAZMORRA_ALTO) {  // Si no hay sitio para el sacerdote, no se pone
            sacerdote = null;
        } else {
            sacerdote = new Sacerdote(sacerdoteX, sacerdoteY, random.nextInt(3));
            campos[sacerdoteX][sacerdoteY] = true;
        }
    }

    private boolean cerca(int sacerdoteX, int sacerdoteY, Esqueleto vampiro) {
        return abs(sacerdoteX - vampiro.x) + abs(sacerdoteY - vampiro.y) <= 1;  // Esto devuelve true cuando el sacerdote está en las cuatro casillas adyacentes al vampiro (arriba, abajo, izquierda y derecha)
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;

            // Actualizar la matriz campos tras cada desplazamiento
            Esqueleto ultimo = monstruos.partes.get(monstruos.partes.size()-1);
            campos[ultimo.x][ultimo.y] = false;     // El último deja libre su casilla

            // Avanzar
            monstruos.avance();

            // Actualizar la matriz campos tras cada desplazamiento
            Esqueleto vampiro = monstruos.partes.get(0);
            campos[vampiro.x][vampiro.y] = true;    // El vampiro ocupa una nueva casilla

            // Comprobar si el vampiro se choca con sus esqueletos
            if (monstruos.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            // Comprobar si el vampiro absorbe un alma
            if (vampiro.x == alma.x && vampiro.y == alma.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                monstruos.abordaje();

                // Se actualiza la matriz campos
                Esqueleto nuevo = monstruos.partes.get(monstruos.partes.size()-1);
                campos[nuevo.x][nuevo.y] = true;    // Se genera un nuevo esqueleto en el lugar del último así que se vuelve a ocupar ese lugar
                // campos[alma.x][alma.y] = false;  // El alma no se pone a false porque es el sitio que ocupa ahora el vampiro
                if (sacerdote != null) campos[sacerdote.x][sacerdote.y] = false;

                if (monstruos.partes.size() == MAZMORRA_ANCHO * MAZMORRA_ALTO - 1) {    // El menos 1 es para tener en cuenta la existencia de un último sacerdote
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

    // Comprueba si hay algún objeto en la esquina superior izquierda para poner el botón de pausa transparente y poder verlo
    public boolean esquinaOcupada() {
        boolean ocupado = false;

        int i = 0;
        while (!ocupado && i < 2) {
            int j = 0;
            while (!ocupado && j < 2) {
                if (campos[i][j]) {
                    ocupado = true;
                }
                j++;
            }
            i++;
        }

        return ocupado;
    }
}

