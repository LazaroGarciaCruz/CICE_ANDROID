package com.example.lzarogarcacruz.cice_practica_invierno;

import android.content.res.Resources;

/**
 * Created by Lázaro García Cruz on 28/12/2016.
 */

public class Controlador {

    private static Controlador controlador;

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

}
