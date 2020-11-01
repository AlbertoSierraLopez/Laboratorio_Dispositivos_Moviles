package com.ldm.practica1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.View.INVISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreguntaImagenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreguntaImagenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declaración de variables
    private static final int NPREGUNTAS = 5;

    private TextView numeroPregunta;
    private TextView pregunta;
    private List<ImageView> imagenes;

    private Button reiniciar;
    private Button ayuda;
    private Button siguiente;

    private int contadorPreguntas;
    private int puntuacion;


    private LibreriaPreguntas libreria = new LibreriaPreguntas();

    private View vista;

    public PreguntaImagenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreguntaImagenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreguntaImagenFragment newInstance(String param1, String param2) {
        PreguntaImagenFragment fragment = new PreguntaImagenFragment();
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
        vista = inflater.inflate(R.layout.fragment_pregunta_imagen, container, false);

        // Recoger la información compartida
        contadorPreguntas = ((MainActivity) getActivity()).getContadorPreguntas();
        puntuacion = ((MainActivity) getActivity()).getPuntuacion();

        // Conectar la parte lógica con el diseño
        numeroPregunta = vista.findViewById(R.id.txtNumeroPregunta);
        pregunta = vista.findViewById(R.id.txtPregunta);

        // imagenes guarda los ImageViews que componen las respuestas a la pregunta
        imagenes = new ArrayList<>();
        imagenes.add((ImageView) vista.findViewById(R.id.img0));
        imagenes.add((ImageView) vista.findViewById(R.id.img1));
        imagenes.add((ImageView) vista.findViewById(R.id.img2));
        imagenes.add((ImageView) vista.findViewById(R.id.img3));

        // Listeners para las imagenes
        imagenes.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick(v);
            }
        });
        imagenes.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick(v);
            }
        });
        imagenes.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick(v);
            }
        });
        imagenes.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClick(v);
            }
        });

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
                    if (libreria.getEspecial(contadorPreguntas)) {
                        cicloDeJuego();
                    } else {
                        Navigation.findNavController(vista).navigate(R.id.preguntaImagenFragment);
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

        cicloDeJuego();

        return vista;
    }

    public void cicloDeJuego() {
        // Mostrar pregunta
        String sPregunta = getResources().getString(R.string.txtPregunta, contadorPreguntas + 1);   // String con valores variables que se rellenan ahora con el numero de la pregunta
        numeroPregunta.setText(sPregunta);
        // En lugar de poner el string directamente por lo visto es mejor llamarlo desde strings.xml
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

        // Mostrar respuestas
        int [] respuestas = new int[4];
        respuestas[0] = Integer.parseInt(libreria.getRespuesta0(contadorPreguntas));    // Como la dirección está guardada como un String hay que desencapsularlo antes de guardarlo
        respuestas[1] = Integer.parseInt(libreria.getRespuesta1(contadorPreguntas));
        respuestas[2] = Integer.parseInt(libreria.getRespuesta2(contadorPreguntas));
        respuestas[3] = Integer.parseInt(libreria.getRespuesta3(contadorPreguntas));

        for (int i = 0; i < 4; i++) {
            // A cada ImageView se le asigna un recurso guardado en respuestas
            imagenes.get(i).setImageResource(respuestas[i]);
        }
    }

    public void onImageViewClick(View view) {
        // pulsado es el indice del array imagenes que ocupa elementoPulsado, que luego comparamos con solucion para saber si es el correcto o no
        ImageView elementoPulsado = vista.findViewById(view.getId());
        int indicePulsado = imagenes.indexOf(elementoPulsado);

        if (indicePulsado == libreria.getSolucion(contadorPreguntas)) {
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

        // Las imageViews incorrectas se hacen desaparecer para revelar la correcta
        for (int i = 0; i < imagenes.size(); i++) {
            if (i != libreria.getSolucion(contadorPreguntas)) {
                imagenes.get(i).setVisibility(INVISIBLE);
            }
            // Y se elimina la posibilidad de pulsar varias veces en las respuestas para acumular puntos
            imagenes.get(i).setEnabled(false);
        }
    }

}