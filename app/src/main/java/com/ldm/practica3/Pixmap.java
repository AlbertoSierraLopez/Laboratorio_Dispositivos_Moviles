package com.ldm.practica3;

import com.ldm.practica3.Graficos.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}

