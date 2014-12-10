package com.izv.sqlite;
import android.provider.BaseColumns;

//cada tabla es una clase, cad constante es una
public class Contrato{
    private Contrato (){
    }
    public static abstract class TablaJugador implements BaseColumns{
        //SQLite
        //_id no se tiene que poner, implements basecolumns lo pone solo
        //CONSTANTES EN MAYUS
        //el primer campo siempre sera autoincrement_id y no se pone
        public static final String TABLA="Jugador";
        public static final String NOMBRE="nombre";
        public static final String TELEFONO="telefono";
        public static final String VALORACION="valoracion";
        public static final String FNAC="fnac";//fecha de nacimiento
    }

    public static abstract class TablaPartido implements BaseColumns{
        public static final String TABLA="Partido";
        public static final String IDJUGADOR="IDJugador";
        public static final String CONTRINCANTE="contrincante";
        public static final String VALORACION="valoracion";
    }

}