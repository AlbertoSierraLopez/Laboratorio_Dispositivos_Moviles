package com.ldm.practica1;

public class LibreriaPreguntas {

/*    String datosQuiz[][] = {
            {"¿Qué líder tribal luchó contra la ocupación romana de Britania?", "Boudica", "Tácito", "Ariovistus", "Prasutagus"},
            {"¿Qué emperador romano legalizó el cristianismo y puso fin a la persecución de los cristianos?", "Constantino", "Adriano", "Trajano", "Nerón"},
            {"¿Quién fue el primer presidente de los Estados Unidos?", "George Washington", "Thomas Jefferson", "Abraham Lincoln", "Andrew Jackson"},
            {"¿En qué batalla fue finalmente derrotado Napoleón Bonaparte?", "La batalla de Waterloo", "La batalla del Álamo", "La batalla de Stalingrado", "La batalla de Hastings"},
            {"¿A qué filósofo griego se le atribuye la obra \"La República\"?", "Platón", "Sócrates", "Aristóteles", "Ptolomeo"},
            {"¿Qué científico es considerado el padre de la bomba atómica?", "Robert Oppenheimer", "Albert Einstein", "Jonas Salk", "Leó Szilárd"},
            {"¿Quién fue el primer humano en viajar al espacio?", "Yuri Gagarin", "Neil Armstrong", "Buzz Aldrin", "Adriyan Nikolayev"}
    };
*/

    private String[] preguntas = {
            "¿Qué emperador romano legalizó el cristianismo y puso fin a la persecución de los cristianos?",
            "¿A qué filósofo griego se le atribuye la obra \"La República\"?",
            "¿Cuál es el nombre del primer humano en viajar al espacio?",
            "¿En qué lugar nació este famoso estadista francés?"
    };

    private int[] imagenes = {
            R.drawable.constantino_i,
            0,
            0,
            R.drawable.el_emperador_napoleon
    };

    private String[][] respuestas = {
            {"Constantino", "Adriano", "Trajano", "Nerón"},
            {"Aristóteles", "Ptolomeo", "Platón", "Sócrates"},
            {"Buzz Aldrin", "Yuri Gagarin", "Neil Armstrong", "Adriyan Nikolayev"},
            {"Waterloo", "Milán", "Marsella", "Córcega"}
    };

    private int[] soluciones = {
            0,
            2,
            1,
            3
    };



    public String getPregunta(int i) {
        return preguntas[i];
    }

    public int getImagen(int i) {
        return imagenes[i];
    }

    public String getRespuesta0(int i) {
        return respuestas[i][0];
    }

    public String getRespuesta1(int i) {
        return respuestas[i][1];
    }

    public String getRespuesta2(int i) {
        return respuestas[i][2];
    }

    public String getRespuesta3(int i) {
        return respuestas[i][3];
    }

    public int getSolucion(int i) {
        return soluciones[i];
    }
}
