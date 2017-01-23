package com.example.lzarogarcacruz.cice_practica_invierno.Vistas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lzarogarcacruz.cice_practica_invierno.Controlador.BaseDatos;
import com.example.lzarogarcacruz.cice_practica_invierno.Controlador.Controlador;
import com.example.lzarogarcacruz.cice_practica_invierno.R;

public class LoginActivity extends AppCompatActivity {

    private BaseDatos baseDatos;
    private Activity activity;
    private TextView textoInfo;
    private int totalIntentos = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        baseDatos = Controlador.getBaseDatos();
        activity = this;

        textoInfo = (TextView)findViewById(R.id.textoInfo);

        Button botonAcceder = (Button) findViewById(R.id.botonAcceder);
        botonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarAcceso();
            }
        });

        Button botonRegistrarse = (Button) findViewById(R.id.botonRegistrarse);
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarRegistro();
            }
        });

        comprobarAccesoInmediato();

    }

    /**
     * En este metodo se verifica si el usuario selecciono la opcion de
     * mantenerse conectado. En caso positivo se pasa a la pantalla
     * principal de la aplicacion de forma inmediata
     * */
    private void comprobarAccesoInmediato() {

        SharedPreferences sharedPreferences = Controlador.getSharedPreferences();
        boolean isRegistrado = sharedPreferences.getBoolean(Controlador.CLAVE_REGISTRO, false);

        if (isRegistrado) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void verificarAcceso() {

        String textoUsuario = ((EditText)findViewById(R.id.textoUsuario)).getText().toString();
        String textoPassword = ((EditText)findViewById(R.id.textoPassword)).getText().toString();

        textoInfo.setVisibility(View.INVISIBLE);
        textoInfo.setText("");

        boolean isChecked = ((CheckBox)findViewById(R.id.checkBox)).isChecked();

        //Mediante este codigo ocultamos el teclado en caso de que este visible
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        switch (baseDatos.verificarUsuario(textoUsuario, textoPassword)) {

            case Controlador.USUARIO_CORRECTO:

                if (isChecked) {
                    SharedPreferences sharedPreferences = Controlador.getSharedPreferences();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Controlador.CLAVE_REGISTRO, true);
                    editor.commit();
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;

            case Controlador.USUARIO_NO_EXISTE:

                textoInfo.setText("Usuario no encontrado");
                mostrarTexto();

                break;

            case Controlador.PASSWORD_INCORRECTO:

                totalIntentos -= 1;

                if (totalIntentos == 0) {
                    this.finishAffinity();
                }

                textoInfo.setText("¡Password incorrecto! \nIntentos restantes: " + totalIntentos);
                mostrarTexto();

                break;

        }

    }

    private void verificarRegistro() {

        String textoUsuario = ((EditText)findViewById(R.id.textoUsuario)).getText().toString();
        String textoPassword = ((EditText)findViewById(R.id.textoPassword)).getText().toString();
        boolean isChecked = ((CheckBox)findViewById(R.id.checkBox)).isChecked();

        Controlador.getBaseDatos().insertarUsuario(textoUsuario, textoPassword);

        if (isChecked) {
            SharedPreferences sharedPreferences = Controlador.getSharedPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Controlador.CLAVE_REGISTRO, true);
            editor.commit();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void mostrarTexto() {

        runOnUiThread(new Runnable() {
            public void run() {

            Animation animation = AnimationUtils.makeInAnimation(activity.getApplicationContext(), true);
            animation.setDuration(2000);
            textoInfo.startAnimation(animation);
            textoInfo.setVisibility(View.VISIBLE);

            }
        });

    }

}
