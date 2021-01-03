package com.ldm.practica3.juego;

import com.ldm.practica3.Juego;
import com.ldm.practica3.Graficos;
import com.ldm.practica3.Pantalla;
import com.ldm.practica3.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.ayuda4 = g.newPixmap("ayuda4.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.vampiroarriba = g.newPixmap("vampiroarriba.png", PixmapFormat.ARGB4444);
        Assets.vampiroizquierda = g.newPixmap("vampiroizquierda.png", PixmapFormat.ARGB4444);
        Assets.vampiroabajo = g.newPixmap("vampiroabajo.png", PixmapFormat.ARGB4444);
        Assets.vampiroderecha = g.newPixmap("vampiroderecha.png", PixmapFormat.ARGB4444);
        Assets.esqueleto1 = g.newPixmap("esqueleto1.png", PixmapFormat.ARGB4444);
        Assets.esqueleto2 = g.newPixmap("esqueleto2.png", PixmapFormat.ARGB4444);
        Assets.esqueleto3 = g.newPixmap("esqueleto3.png", PixmapFormat.ARGB4444);
        Assets.sacerdote1 = g.newPixmap("sacerdote1.png", PixmapFormat.ARGB4444);
        Assets.sacerdote2 = g.newPixmap("sacerdote2.png", PixmapFormat.ARGB4444);
        Assets.sacerdote3 = g.newPixmap("sacerdote3.png", PixmapFormat.ARGB4444);
        Assets.alma1 = g.newPixmap("alma1.png", PixmapFormat.ARGB4444);
        Assets.alma2 = g.newPixmap("alma2.png", PixmapFormat.ARGB4444);
        Assets.explosion = g.newPixmap("explosion.png", PixmapFormat.ARGB4444);

        Assets.cursor = juego.getAudio().nuevoSonido("cursor.ogg");
        Assets.magia = juego.getAudio().nuevoSonido("magia.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        Assets.victoria = juego.getAudio().nuevoSonido("victoria.ogg");
        Assets.ambiente = juego.getAudio().nuevaMusica("ambiente.ogg");

        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}