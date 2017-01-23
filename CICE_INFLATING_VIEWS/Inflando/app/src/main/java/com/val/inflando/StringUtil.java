package com.val.inflando;

/**
 * Created by vale on 11/05/16.
 */
public class StringUtil {

    public static String mensajeSalida (String nombre)
    {
        String mensaje = null;

            mensaje = "El nombre tiene " +nombre.length() + " letras";

        return mensaje;
    }

    public static String invertirCadena(String cadena) {

        String salida = "";
        char[] caracteres = cadena.toCharArray();

        int posicion = caracteres.length-1;

        do {
            salida += caracteres[posicion];
            posicion--;
        } while (posicion >= 0);

        return salida;

    }

}
