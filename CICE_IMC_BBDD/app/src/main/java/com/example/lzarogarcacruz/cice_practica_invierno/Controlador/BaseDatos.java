package com.example.lzarogarcacruz.cice_practica_invierno.Controlador;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lazarogarciacruz on 23/1/17.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final String sqlCreateTablaUsuarios = "" +
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "usuario TEXT," +
            "password TEXT)";

    public BaseDatos(String nombre, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(App.getContext(), nombre, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(sqlCreateTablaUsuarios);
        } catch (Exception e) {
            Log.d(getClass().getCanonicalName(), e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //Este metodo se encarga de llevar en la base de datos los cambios
        //necesarios para actualizar a la nueva version

        //1. Extraer los datos de la vieja version
        //2. Crear la nueva version de la base de datos
        //3. Copiar los datos antiguos a la nueva version

    }

    public void cerrarBaseDatos(SQLiteDatabase db) {
        db.close();
    }

    public void insertarUsuario(String usuario, String password) {

        //La clase SQLiteOpenHelper ofrece instancias de la base de datos
        //En modo lectura/escritura (getWritableDatabase)
        //En modo solo lectura (getReadableDatabase)

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("" +
                "INSERT INTO usuarios (usuario, password)" +
                "VALUES ('" + usuario + "', '" + password + "')");

        cerrarBaseDatos(db);

    }

    public int verificarUsuario(String usuario, String password) {

        String consulta = "SELECT usuario, password FROM usuarios WHERE usuario = '" + usuario + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        int estado = Controlador.USUARIO_NO_EXISTE;

        try {
            while (cursor.moveToNext()) {

                String password_bbdd = cursor.getString(cursor.getColumnIndex("password"));
                if (password_bbdd.equals(password)) {
                    estado = Controlador.USUARIO_CORRECTO;
                } else {
                    estado = Controlador.PASSWORD_INCORRECTO;
                }

            }
        } finally {
            cursor.close();
            this.cerrarBaseDatos(db);
        }

        return estado;

    }

}

