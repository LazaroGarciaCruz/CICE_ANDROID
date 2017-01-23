package com.val.inflando;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class EscuchaBoton implements View.OnClickListener{


    private Context ct;




    public EscuchaBoton (Context context)
    {
        this.ct = context;
    }

    @Override
    public void onClick(View v) {

        Log.d(getClass().getCanonicalName(), "Ha tocado el botón");

        Activity a = (Activity)ct;

        EditText editText = (EditText)a.findViewById(R.id.editText);

        String nombre = editText.getText().toString();

        Log.d(getClass().getCanonicalName(), "Ha introducido el nombre = " + nombre);

        //String mensaje = StringUtil.mensajeSalida(nombre);
        String mensaje = StringUtil.invertirCadena(nombre);

        ViewGroup caja_resultado = (ViewGroup)a.findViewById(R.id.resultado);

        if (caja_resultado.getChildCount()>0) //la lista ya ha sido inflada
        {
            TextView text = (TextView)a.findViewById(R.id.mensaje_salida);
            text.setText(mensaje);

        } else {


            LayoutInflater layoutInflater = a.getLayoutInflater(); //o LayoutInflater.from(a)
            View vista_inflada = layoutInflater.inflate(R.layout.mensaje_salida,caja_resultado);
            TextView text = (TextView)vista_inflada.findViewById(R.id.mensaje_salida);
            text.setText(mensaje);

        }


       mostrarLayout(a.findViewById(R.id.principal_layout));

    }

    private void mostrarLayout(View vista)
    {
        Log.d(getClass().getCanonicalName(), vista.getClass().getCanonicalName());

        if (vista instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) vista;

            for (int i = 0; i<viewGroup.getChildCount(); i++)
            {
                View vistahija = viewGroup.getChildAt(i);
                mostrarLayout(vistahija);

            }
        }
    }


}
