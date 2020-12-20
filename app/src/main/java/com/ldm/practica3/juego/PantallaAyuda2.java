package com.ldm.practica3.juego;

import java.util.List;

import com.ldm.practica3.Juego;
import com.ldm.practica3.Graficos;
import com.ldm.practica3.Input.TouchEvent;
import com.ldm.practica3.Pantalla;


public class PantallaAyuda2 extends Pantalla {
    public PantallaAyuda2(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new PantallaAyuda3(juego));
                    if (Configuraciones.sonidoHabilitado)
                        Assets.cursor.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.ayuda2, 64, 100);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
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
