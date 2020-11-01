package com.ldm.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    private static final int NPREGUNTAS = 5;

    private TextView numeroPregunta;
    private TextView pregunta;
    private ListView listViewRespuestas;
    private Button reiniciar;
    private Button ayuda;
    private Button siguiente;

    private int contadorPreguntas;
    private int puntuacion;

    List<String> listaRespuestas;

    private LibreriaPreguntas libreria = new LibreriaPreguntas();

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

        // Recoger la información compartida
        contadorPreguntas = ((MainActivity) getActivity()).getContadorPreguntas();
        puntuacion = ((MainActivity) getActivity()).getPuntuacion();

        // Conectar la parte lógica con el diseño
        numeroPregunta = vista.findViewById(R.id.txtNumeroPregunta);
        pregunta = vista.findViewById(R.id.txtPregunta);
        listViewRespuestas = vista.findViewById(R.id.listViewRespuestas);

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
                ((MainActivity) getActivity()).setContadorPreguntas(contadorPreguntas);

                if (contadorPreguntas == NPREGUNTAS) {
                    Intent intentFinalizar = new Intent(getActivity(), ResultadosActivity.class);
                    // Además de llamar a la activity hay que pasarle el dato de la puntuación para que lo pueda mostrar allí
                    intentFinalizar.putExtra("puntuacionFinal", puntuacion);
                    startActivity(intentFinalizar);
                } else {
                    // Si la siguiente pregunta es especial, hay que hacer un intent y seguir en otra activity
                    if (libreria.getEspecial(contadorPreguntas)) {
                        Navigation.findNavController(vista).navigate(R.id.preguntaImagenFragment);
                    } else {
                        // Si no, seguimos aquí
                        cicloDeJuego();
                    }
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
        pregunta.setText(libreria.getPregunta(contadorPreguntas));

        // Cargar imagen si la hubiera
        int idImagen = libreria.getImagen(contadorPreguntas);
        // idImagen puede ser una imagen guardada en R o un 0, si es un 0 se borra la imagen que hubiese de la pregunta anterior
        pregunta.setCompoundDrawablesWithIntrinsicBounds(idImagen, 0, 0, 0);
        // El texto se pega a la derecha si hay una imagen o se centra si no la hay
        if (idImagen != 0) {
            pregunta.setGravity(Gravity.START);
        } else {
            pregunta.setGravity(Gravity.CENTER);
        }

        // Cargar la lista de respuestas
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(libreria.getRespuesta0(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta1(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta2(contadorPreguntas));
        listaRespuestas.add(libreria.getRespuesta3(contadorPreguntas));

        // Configurar el adaptador para mostrar las respuestas
        ArrayAdapter<String> adaptadorListaRespuestas = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listaRespuestas);
        listViewRespuestas.setAdapter(adaptadorListaRespuestas);
        listViewRespuestas.setOnItemClickListener(this);
        listViewRespuestas.setEnabled(true);    // Esto activa de nuevo el listView en caso de que hubiese sido desactivado
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == libreria.getSolucion(contadorPreguntas)) {
            Toast toast = Toast.makeText(getActivity(), "RESPUESTA CORRECTA", Toast.LENGTH_LONG);
            // Quiero que el toast aparezca encima de los botones para que no moleste
            toast.setGravity(Gravity.BOTTOM, 0, 180);
            toast.show();

            puntuacion += 3;
            ((MainActivity) getActivity()).setPuntuacion(puntuacion);
        } else {
            Toast toast = Toast.makeText(getActivity(), "RESPUESTA INCORRECTA", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 180);
            toast.show();

            puntuacion -= 2;
            ((MainActivity) getActivity()).setPuntuacion(puntuacion);
        }

        // Cambiar los colores de las respuestas para revelar la correcta
        for (int i = 0; i < listViewRespuestas.getChildCount(); i++) {
            if (i == libreria.getSolucion(contadorPreguntas)) {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#A2D2FF"));
            } else {
                listViewRespuestas.getChildAt(i).setBackgroundColor(Color.parseColor("#FFAFCC"));
            }
        }

        // Solo se puede responder una vez
        listViewRespuestas.setEnabled(false);   // Desactiva el listView para no responder múltiples veces a la misma pregunta y ganar puntos extra
    }
}