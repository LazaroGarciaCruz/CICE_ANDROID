package com.example.lzarogarcacruz.cice_practica_invierno;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lázaro García Cruz on 28/12/2016.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

}
