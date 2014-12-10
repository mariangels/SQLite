package com.izv.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "futbol.sqlite";
    public static final int DATABASE_VERSION =3;// version!

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //es la version 1, esto no hace falta
        String sqlite;

        /*String sql = "drop table if exists " + Contrato.TablaAgenda.TABLA;
        db.execSQL(sql);
        onCreate(db);*/

        //transformar esquema de la version old a la version now , sin que se produzca perdida de datos

        //1. crear tabla de respaldo(identicas)
        sqlite="create table respaldo(" +
                Contrato.TablaJugador._ID + " integer primary key autoincrement, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.VALORACION + " integer, " +
                Contrato.TablaJugador.FNAC + " text) ";
        db.execSQL(sqlite);

        //2. copio los datos a esas tablas
        sqlite=" insert into table respaldo select * from "+Contrato.TablaJugador.TABLA;
        db.execSQL(sqlite);

        //3. borrar las tablas originales
        sqlite="drop table "+Contrato.TablaJugador.TABLA;
        db.execSQL(sqlite);

        //4. creo las tablas nuevas llamando a oncreate
        onCreate(db);

        //5. copio los datos de las tablas de respaldo a mis tablas
        sqlite=" insert into table "+Contrato.TablaJugador.TABLA+
                " select " +
                Contrato.TablaJugador._ID + ", "+
                Contrato.TablaJugador.NOMBRE + ", " +
                Contrato.TablaJugador.TELEFONO + ", " +
                Contrato.TablaJugador.FNAC +
                " from respaldo";
        db.execSQL(sqlite);

        sqlite=" insert into table "+Contrato.TablaPartido.TABLA+
                " values( " +
                    Contrato.TablaPartido.IDJUGADOR + ", " +
                    Contrato.TablaPartido.VALORACION + ", " +
                    Contrato.TablaPartido.CONTRINCANTE + ")" +
                " select " +
                    Contrato.TablaJugador._ID + ", "+
                    Contrato.TablaJugador.VALORACION +
                " from respaldo," +
                " contrincanteInicial ";
        db.execSQL(sqlite);

        //6. borro las tablas de respaldo
        sqlite="drop table respaldo";
        db.execSQL(sqlite);

        //no hay alter table
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlite;

        //firefox -> sqlite manager
        sqlite="create table "+Contrato.TablaJugador.TABLA+"( " +
                Contrato.TablaJugador._ID+" integer primary key autoincrement, "+
                Contrato.TablaJugador.NOMBRE+" text, " +
                Contrato.TablaJugador.TELEFONO+" text, " +
                Contrato.TablaJugador.FNAC+" text" +
                ") ";
        db.execSQL(sqlite);

        Log.v("sql", sqlite);

        sqlite="create table "+Contrato.TablaPartido.TABLA+"( " +
                Contrato.TablaPartido._ID+" integer primary key autoincrement,  "+
                Contrato.TablaPartido.IDJUGADOR+" integer, "+
                Contrato.TablaPartido.CONTRINCANTE+" text, "+
                Contrato.TablaPartido.VALORACION+" integer "+
                ") ";
        db.execSQL(sqlite);

        Log.v("sql", sqlite);
    }
}