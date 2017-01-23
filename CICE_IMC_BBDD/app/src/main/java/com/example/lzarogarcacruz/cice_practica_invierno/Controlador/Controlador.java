package com.example.lzarogarcacruz.cice_practica_invierno.Controlador;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lzarogarcacruz.cice_practica_invierno.R;

/**
 * Created by Lázaro García Cruz on 28/12/2016.
 */

public class Controlador {

    public static final String CLAVE_REGISTRO = "is_registrado";
    public static final int USUARIO_NO_EXISTE = 0;
    public static final int PASSWORD_INCORRECTO = 1;
    public static final int USUARIO_CORRECTO = 2;

    private static Controlador controlador;
    private static BaseDatos baseDatos;
    private static SharedPreferences sharedPreferences;

    private String listaRangos[] = {
            App.getContext().getResources().getString(R.string.textoRango1),
            App.getContext().getResources().getString(R.string.textoRango2),
            App.getContext().getResources().getString(R.string.textoRango3),
            App.getContext().getResources().getString(R.string.textoRango4),
            App.getContext().getResources().getString(R.string.textoRango5)
    };

    public static Controlador getControlador() {
        if (controlador == null){
            controlador = new Controlador();
        }
        return controlador;
    }

    public static String getValorRango(int imc) {

        if (imc < 16) {
            return App.getContext().getResources().getString(R.string.textoEstado1);
        } else if (16 <= imc && imc < 18) {
            return App.getContext().getResources().getString(R.string.textoEstado2);
        } else if (18 <= imc && imc < 25) {
            return App.getContext().getResources().getString(R.string.textoEstado3);
        } else if (25 <= imc && imc < 31) {
            return App.getContext().getResources().getString(R.string.textoEstado4);
        } else if (imc >= 31) {
            return App.getContext().getResources().getString(R.string.textoEstado5);
        }

        return "";

    }

    public String[] getListaRangos() {
        return listaRangos;
    }

    public static BaseDatos getBaseDatos() {

        if (baseDatos == null) {
            baseDatos = new BaseDatos("BaseDatosUsuarios", null, 1);
        }

        return baseDatos;

    }

    public static SharedPreferences getSharedPreferences() {

        if (sharedPreferences == null) {
            sharedPreferences = App.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        }

        return sharedPreferences;

    }

}
