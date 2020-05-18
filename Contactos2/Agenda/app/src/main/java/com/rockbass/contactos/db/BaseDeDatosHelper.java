package com.rockbass.contactos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatosHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_BASEDEDATOS = "basededatos.db";
    public static final int VERSION = 1;

    private static final String QUERY_CREACION_TABLA_CONTACTOS =
            "CREATE TABLE "+ UsuarioContract.UsuarioEntry.TABLA_CONTACTO+"("
                    + UsuarioContract.UsuarioEntry._ID + " INTEGER PRIMARY KEY,"
                    + UsuarioContract.UsuarioEntry.CAMPO_NOMBRE + " TEXT,"
                    + UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO + " TEXT,"
                    + UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO + " TEXT,"
                    + UsuarioContract.UsuarioEntry.CAMPO_EDAD + " INTEGER,"
                    + UsuarioContract.UsuarioEntry.CAMPO_TELEFONO + " TEXT,"
                    + UsuarioContract.UsuarioEntry.CAMPO_EMAIL + " TEXT,"
                    + UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA + " INTERGER);";

    private static final String QUERY_BORRADO_TABLA_CONTACTOS =
            "DROP TABLE IF EXISTS "+ UsuarioContract.UsuarioEntry.TABLA_CONTACTO+";";
    public BaseDeDatosHelper(final Context context){
        super(context, NOMBRE_BASEDEDATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREACION_TABLA_CONTACTOS);
        ContentValues valores = new ContentValues();

        valores.put(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE,"Juan");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO,"Lopez");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO,"Perez");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_EDAD,"22");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO,"6632547856");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_EMAIL,"juanP@gmail.com");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA,"Maria");

        db.insertOrThrow(UsuarioContract.UsuarioEntry.TABLA_CONTACTO, null, valores);

        valores.put(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE,"Maria");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO,"Lopez");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO,"Marquez");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_EDAD,"21");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO,"6632547855");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_EMAIL,"mariaL@gmail.com");
        valores.put(UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA,"Juan");

        db.insertOrThrow(UsuarioContract.UsuarioEntry.TABLA_CONTACTO, null, valores);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_BORRADO_TABLA_CONTACTOS);
        onCreate(db);
    }
}
