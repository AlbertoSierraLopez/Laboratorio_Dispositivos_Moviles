package com.ldm.practica3.juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monstruos {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;

    public List<Esqueleto> partes = new ArrayList<Esqueleto>();
    public int direccion;

    private Random r = new Random();

    public Monstruos() {
        direccion = ARRIBA;
        partes.add(new Esqueleto(5, 6, Esqueleto.TIPO_0)); // El vampiro es tipo_0
        partes.add(new Esqueleto(5, 7, r.nextInt(3)));
        partes.add(new Esqueleto(5, 8, r.nextInt(3)));
    }

    public void girarIzquierda() {
        direccion += 1;
        if (direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if (direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void abordaje() {
        Esqueleto end = partes.get(partes.size()-1);
        partes.add(new Esqueleto(end.x, end.y, r.nextInt(3)));
    }

    public void avance() {
        Esqueleto vampiro = partes.get(0);

        int len = partes.size() - 1;
        for (int i = len; i > 0; i--) {
            Esqueleto antes = partes.get(i-1);
            Esqueleto parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if (direccion == ARRIBA)
            vampiro.y -= 1;
        if (direccion == IZQUIERDA)
            vampiro.x -= 1;
        if (direccion == ABAJO)
            vampiro.y += 1;
        if (direccion == DERECHA)
            vampiro.x += 1;

        if (vampiro.x < 0)
            vampiro.x = 9;
        if (vampiro.x > 9)
            vampiro.x = 0;
        if (vampiro.y < 0)
            vampiro.y = 12;
        if (vampiro.y > 12)
            vampiro.y = 0;
    }

    public boolean comprobarChoque() {
        int len = partes.size();
        Esqueleto vampiro = partes.get(0);
        for (int i = 1; i < len; i++) {
            Esqueleto parte = partes.get(i);
            if (parte.x == vampiro.x && parte.y == vampiro.y)
                return true;
        }
        return false;
    }
}

