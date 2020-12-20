package com.ldm.practica3.juego;

public class Esqueleto {
    public static final int TIPO_0 = 4; // El vampiro
    public static final int TIPO_1 = 0;
    public static final int TIPO_2 = 1;
    public static final int TIPO_3 = 2;
    public int x, y;
    int tipo;

    public Esqueleto(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }
}

