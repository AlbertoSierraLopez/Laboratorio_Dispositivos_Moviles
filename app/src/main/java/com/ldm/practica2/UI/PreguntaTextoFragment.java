package com.ldm.practica2.UI;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ldm.practica2.Constants.Constants;
import com.ldm.practica2.Database.AdminSQLiteOpenHelper;
import com.ldm.practica2.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreguntaTextoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreguntaTextoFragment extends Fragment implements AdapterView.OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Declaración de variables
    private int contadorPreguntas = 0;
    private int puntuacion = 0;
    private String nombre;

    private TextView numeroPregunta;
    private TextView pregunta;
    private ListView listViewRespuestas;
    private Button reiniciar;
    private Button ayuda;
    private Button siguiente;

    List<String> listaRespuestas;

    private Cursor cursor;

    private SoundPool spAcierto;
    private SoundPool spFallo;
    private int sonidoAcierto;
    private int sonidoFallo;
    private MediaPlayer musica;

    private View vista;

    public PreguntaTextoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreguntaTextoFragment.
     */
    public static PreguntaTextoFragment newInstance(String param1, String param2) {
        PreguntaTextoFragment fragment = new PreguntaTextoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_pregunta_texto, container, false);

        // Conectar la parte lógica con el diseño
        numeroPregunta = vista.findViewById(R.id.txtNumeroPregunta);
        pregunta = vista.findViewById(R.id.txtPregunta);
        listViewRespuestas = vista.findViewById(R.id.listViewRespuestas);

        // Activar música
        musica = MediaPlayer.create(getContext(), R.raw.greek_music);
        musica.setLooping(true);
        musica.start();

        // Cargar los sonidos
        spAcierto = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonidoAcierto = spAcierto.load(getContext(), R.raw.correct, 1);

        spFallo = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonidoFallo = spFallo.load(getContext(), R.raw.wrong, 1);

        // Base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), Constants.DATABASE_NAME, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        // Orden de las preguntas aleatorio
        cursor = db.rawQuery("select * from " + Constants.DATABASE_TABLE_NAME + " order by random()", null);
        cursor.moveToFirst();

        // Establecer los listeners para los botones
        reiniciar = vista.findViewById(R.id.btnReiniciar);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReiniciar = new Intent(getActivity(), MainActivity.class);
                startActivity(intentReiniciar);
            }
        });

        siguiente = vista.findViewById(R.id.btnSiguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // El juego permite saltar preguntas sin responder
                contadorPreguntas++;

                // Seleccionar siguiente pregunta
                cursor.moveToNext();

                if (contadorPreguntas == Constants.N_PREGUNTAS) {
                    nombre = ((MainActivity) getActivity()).getNombre();

                    musica.stop();

                    Intent intentFinalizar = new Intent(getActivity(), ResultadosActivity.class);
                    // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
                    intentFinalizar.putExtra("puntuacionFinal", puntuacion);
                    intentFinalizar.putExtra("nombre", nombre);
                    startActivity(intentFinalizar);
                } else {
                    cicloDeJuego();
                }
            }
        });

        ayuda = vista.findViewById(R.id.btnAyuda);
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(vista, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        // Lanzar el ciclo de juego que se repetirá tantas veces como preguntas haya (NPREGUNTAS)
        cicloDeJuego();

        return vista;
    }

    public void cicloDeJuego() {
        // Mostrar pregunta
        String sPregunta = getResources().getString(R.string.txtPregunta, contadorPreguntas + 1);   // String con valores variables que se rellenan ahora con el numero de la pregunta
        numeroPregunta.setText(sPregunta);
        pregunta.setText(cursor.getString(1));

        // Cargar la lista de respuestas
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(cursor.getString(2));
        listaRespuestas.add(cursor.getString(3));
        listaRespuestas.add(cursor.getString(4));
        listaRespuestas.add(cursor.getString(5));

        // Configurar el adaptador para mostrar las respuestas
        ArrayAdapter<String> adaptadorListaRespuestas = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, listaRespuestas) {
            // Esto sirve para cambiar el layout de los elementos individuales del listview:
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Coger el elemento del listview
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Tamaño del texto
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,26);

                // Return the view
                return view;
            }
        };
        listViewRespuestas.setAdapter(adaptadorListaRespuestas);
        listViewRespuestas.setOnItemClickListener(this);
        listViewRespuestas.setEnabled(true);    // Esto activa de nuevo el listView en caso de que hubiese sido desactivado en la pregunta anterior
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int solucion = cursor.getInt(6);

        if (position == solucion) {
            // Sonido acierto
            spAcierto.play(sonidoAcierto, 1, 1, 1, 0, 0);
            // Mensaje acierto
            Toast toast = Toast.makeText(getActivity(), "RESPUESTA CORRECTA", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 180);   // Quiero que el toast aparezca encima de los botones para que no moleste
            toast.show();

            puntuacion += 3;
        } else {
            // Sonido fallo
            spFallo.play(sonidoFallo, 1, 1, 1, 0, 0);
            // Mensaje fallo
            Toast toast = Toast.makeText(getActivity(), "RESPUESTA INCORRECTA", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 180);   // Quiero que el toast aparezca encima de los botones para que no moleste
            toast.show();

            puntuacion -= 2;
        }

        // Cambiar los colores de las respuestas para revelar la correcta
        for (int i = 0; i < listViewRespuestas.getChildCount(); i++) {
            if (i == solucion) {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#A2D2FF"));
            } else {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#FFAFCC"));
            }
        }

        // Solo se puede responder una vez
        listViewRespuestas.setEnabled(false);   // Desactiva el listView para no responder múltiples veces a la misma pregunta y ganar puntos extra
    }
}