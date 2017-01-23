package com.example.lzarogarcacruz.cice_practica_invierno;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class ResultActivity extends AppCompatActivity {

    private Activity activity;
    private TextView textoResultado1, textoResultado2, textoResultado3;
    private String imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imc = extras.getString("IMC");
        }

        activity = this;
        textoResultado1 = (TextView)findViewById(R.id.textoResultado1);
        textoResultado2 = (TextView)findViewById(R.id.textoResultado2);
        textoResultado3 = (TextView)findViewById(R.id.textoResultado3);
        textoResultado1.setVisibility(View.INVISIBLE);
        textoResultado2.setVisibility(View.INVISIBLE);
        textoResultado3.setVisibility(View.INVISIBLE);

        if (imc.equals("-1")) {
            imc = "X";
            textoResultado2.setText(R.string.textoError);
            textoResultado3.setText("");
        } else {
            textoResultado3.setText(Controlador.getValorRango(Integer.parseInt(imc)));
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        animacionCalculoIMC();

    }

    private void animacionCalculoIMC() {

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        Thread th = new Thread(new Runnable() {

            public void run() {

                int tiempo = 3000;
                while (tiempo > 0) {
                    try {
                        Thread.sleep(tiempo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tiempo-=tiempo;
                }

                lanzarSmallBang();
                mostrarTexto();

            }

        });

        th.start();

    }

    private void lanzarSmallBang() {

        runOnUiThread(new Runnable() {
            public void run() {
                final TextView textoIMC = (TextView)findViewById(R.id.textoIMC);
                final SmallBang mSmallBang = SmallBang.attach2Window(activity);
                mSmallBang.bang(textoIMC,250,new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                        textoIMC.setText(imc);
                    }

                    @Override
                    public void onAnimationEnd() {

                    }
                });
            }
        });

    }

    private void mostrarTexto() {

        runOnUiThread(new Runnable() {
            public void run() {

                Animation animation = AnimationUtils.makeInAnimation(activity.getApplicationContext(), true);
                animation.setDuration(2000);
                textoResultado1.startAnimation(animation);
                textoResultado1.setVisibility(View.VISIBLE);
                animation = AnimationUtils.makeInAnimation(activity.getApplicationContext(), true);
                animation.setDuration(2500);
                textoResultado2.startAnimation(animation);
                textoResultado2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.makeInAnimation(activity.getApplicationContext(), true);
                animation.setDuration(3000);
                textoResultado3.startAnimation(animation);
                textoResultado3.setVisibility(View.VISIBLE);

            }
        });

    }

    private void mostrarListaRangos() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                Controlador.getControlador().getListaRangos());
        ListView listaRangos = new ListView(this);
        listaRangos.setAdapter(adapter);

        AlertDialog.Builder dialog = new AlertDialog.Builder(ResultActivity.this);
        dialog.setView(listaRangos);
        dialog.setCancelable(true);

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE, 0, Menu.NONE, "Info").setIcon(R.drawable.info)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle() == null) {
            return super.onOptionsItemSelected(item);
        }

        switch (item.getTitle().toString()) {
            case "Info":
                mostrarListaRangos();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

}