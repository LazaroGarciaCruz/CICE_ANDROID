package com.example.lzarogarcacruz.cice_practica_invierno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int panelAltura = 0;
    private int panelAnchura = 0;
    private boolean isMostrandoPeso = false;

    public Context context;

    private SeekBar seekBarEstatura;
    private SeekBar seekBarPeso;
    private Button botonAnterior;
    private Button botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        //Se obtienen las referencias al texto que muestra al usuario los valores seleccionados
        //para el peso y la estatura
        TextView textoEstatura = (TextView) findViewById(R.id.textoEstatura);
        TextView textoPeso = (TextView) findViewById(R.id.textoPeso);

        //Se obtiene las referencias a los seekbar para cambiar los valores de peso y estatura
        //y se le asocian las clases que gestionan la represntacion del texto de dicha informacion
        seekBarEstatura = (SeekBar) findViewById(R.id.seekBarEstatura);
        seekBarEstatura.setOnSeekBarChangeListener(new GestorSeekBar(textoEstatura, false));
        seekBarPeso = (SeekBar) findViewById(R.id.seekBarPeso);
        seekBarPeso.setOnSeekBarChangeListener(new GestorSeekBar(textoPeso, true));

        //Se obtiene la referencia al panel que contiene la informacion sobre el peso
        //que sera del mismo tamaño que el panel de la informacion de la estatura
        final RelativeLayout panelPeso = (RelativeLayout)findViewById(R.id.panelPeso);
        //Se obtiene la referencia a la imagen que en cada etapa tapa uno de los paneles con controles
        final ImageView imagen = (ImageView)findViewById(R.id.panelImagen);

        //Mediante este metodo se consigue que cuando al panel del peso se le asigne sus verdaderas medidas
        //en tiempo de ejecucion, podamos alamacenar las medidas en variables y actualizar el tamaño de la imagen
        //de esta forma independientemente del tamaño de la pantalla donde se ejecute la aplicacion, la imagen
        //tendra un tamaño correcto
        panelPeso.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            public void onGlobalLayout() {

                panelAltura = panelPeso.getHeight();
                panelAnchura = panelPeso.getWidth();
                imagen.getLayoutParams().height = panelAltura;
                imagen.getLayoutParams().width = panelAnchura;

            }

        });

        //Se establecen las propiedades del boton de anterior y sigiente
        botonAnterior = (Button)findViewById(R.id.botonAnterior);
        botonAnterior.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        botonAnterior.setClickable(false);
        botonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacionHaciaEstaura();
                isMostrandoPeso = false;
            }
        });

        botonSiguiente = (Button)findViewById(R.id.botonSiguiente);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMostrandoPeso) {
                    animacionHaciaPeso();
                    isMostrandoPeso = true;
                } else {

                    float estatura = (float)seekBarEstatura.getProgress();
                    float peso = (float)seekBarPeso.getProgress();
                    float imc = -1;

                    if (peso > 1 && estatura > 1) {
                        imc = peso / ((estatura/100)*(estatura/100));
                    }

                    Intent intent = new Intent(context, ResultActivity.class);
                    intent.putExtra("IMC", Integer.toString((int)imc));
                    startActivity(intent);

                }
            }
        });

    }

    private void animacionHaciaPeso() {

        final ImageView imagen = (ImageView)findViewById(R.id.panelImagen);

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -panelAltura);
        animation.setDuration(500);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                imagen.clearAnimation();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imagen.getLayoutParams();
                params.gravity = Gravity.TOP;
                imagen.setLayoutParams(params);
                imagen.setImageResource(R.drawable.pesaje2);
            }
        });

        imagen.startAnimation(animation);

        botonAnterior.getBackground().clearColorFilter();
        botonAnterior.invalidate();
        botonAnterior.setClickable(true);
        botonSiguiente.setText(R.string.textoBotonCalcular);

    }

    private void animacionHaciaEstaura() {

        final ImageView imagen = (ImageView)findViewById(R.id.panelImagen);

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, panelAltura);
        animation.setDuration(500);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                imagen.clearAnimation();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imagen.getLayoutParams();
                params.gravity = Gravity.BOTTOM;
                imagen.setLayoutParams(params);
                imagen.setImageResource(R.drawable.altura);
            }
        });

        imagen.startAnimation(animation);

        botonAnterior.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        botonAnterior.invalidate();
        botonAnterior.setClickable(false);
        botonSiguiente.setText(R.string.textoBotonSiguiente);

    }

}
 class GestorSeekBar implements SeekBar.OnSeekBarChangeListener {

    private TextView texto;
    private boolean isTextoPeso;

     GestorSeekBar(TextView texto, boolean isTextoPeso) {
         this.texto = texto;
         this.isTextoPeso = isTextoPeso;
     }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isTextoPeso) {
            texto.setText(progress + " Kg");
        } else {
            texto.setText(progress + " Cm");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}
