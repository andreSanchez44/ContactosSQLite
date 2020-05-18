package com.rockbass.contactos.db;

import android.provider.BaseColumns;

public class UsuarioContract {
    public UsuarioContract(){}


    public static class UsuarioEntry implements BaseColumns {
        public static String TABLA_CONTACTO = "Contactos";
        public static String CAMPO_NOMBRE = "nombre";
        public static String CAMPO_APEPATERNO = "apePaterno";
        public static String CAMPO_APEMATERNO = "apeMaterno";
        public static String CAMPO_EDAD = "edad";
        public static String CAMPO_TELEFONO = "telefono";
        public static String CAMPO_EMAIL = "email";
        public static String CAMPO_CONTACTO_CONFIANZA = "contacto_confianza";

        //public static final String CrearTablaContactos="CREATE TABLE " +
              //  TABLA_CONTACTO + "(" + CAMPO_NOMBRE + CAMPO_APEPATERNO + " TEXT," +" INT," + CAMPO_TELEFONO + " TEXT," + CAMPO_CONTACTO_CONFIANZA + " TEXT)";


    }
}
