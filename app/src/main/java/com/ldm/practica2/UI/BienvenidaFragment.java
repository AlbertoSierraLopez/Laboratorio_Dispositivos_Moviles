package com.ldm.practica2.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ldm.practica2.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienvenidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienvenidaFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button comenzar;
    private Button comenzarContrarreloj;
    private EditText txtNombre;
    private Button ayuda;
    private Button ayudaContrarreloj;

    private View vista;

    public BienvenidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BienvenidaFragment.
     */
    public static BienvenidaFragment newInstance(String param1, String param2) {
        BienvenidaFragment fragment = new BienvenidaFragment();
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
        vista =  inflater.inflate(R.layout.fragment_bienvenida, container, false);

        txtNombre = vista.findViewById(R.id.txtNombre);

        // Rellenar el campo nombre con el último jugador para ahorrar tiempo y no tener que escribirlo cada vez
        SharedPreferences sp = getActivity().getSharedPreferences("lastplayer", Context.MODE_PRIVATE);
        String ultimoJugador = sp.getString("ultimo", "");
        txtNombre.setText(ultimoJugador);

        comenzar = vista.findViewById(R.id.btnComenzar);
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();

                if (nombre.equals("")) {
                    Toast.makeText(getContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                } else {
                    ((MainActivity) getActivity()).setNombre(nombre);
                    Navigation.findNavController(vista).navigate(R.id.preguntaTextoFragment);
                }
            }
        });

        comenzarContrarreloj = vista.findViewById(R.id.btnComenzarContrarreloj);
        comenzarContrarreloj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();

                if (nombre.equals("")) {
                    Toast.makeText(getContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                } else {
                    ((MainActivity) getActivity()).setNombre(nombre);
                    Navigation.findNavController(vista).navigate(R.id.preguntaContrarrelojFragment);
                }
            }
        });

        ayuda = vista.findViewById(R.id.btnAyuda);
        // La ventana tiene el texto de ayuda por defecto
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

        ayudaContrarreloj = vista.findViewById(R.id.btnAyudaContrarreloj);
        ayudaContrarreloj.setOnClickListener(new View.OnClickListener() {
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

                // Cambiar el texto de la ventana por la ayuda para el contrarreloj
                ((TextView) popupWindow.getContentView().findViewById(R.id.txtWindow)).setText(R.string.txtAyudaContrarreloj);

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

        return vista;
    }
}