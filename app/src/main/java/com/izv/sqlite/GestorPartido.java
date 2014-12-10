package com.izv.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GestorPartido {

    private Ayudante ayudante;
    private SQLiteDatabase sqlite;

    public GestorPartido(Context c) {
        ayudante = new Ayudante(c);
    }
    public void open() {
        sqlite = ayudante.getWritableDatabase();
    }
    public void openRead() {
        sqlite = ayudante.getReadableDatabase();
    }
    public void close() {
        ayudante.close();
    }

    public int insert(Partido p) {
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaPartido.IDJUGADOR, p.getIdjugador());
        cv.put(Contrato.TablaPartido.CONTRINCANTE, p.getContrincante());
        cv.put(Contrato.TablaPartido.VALORACION, p.getValoracion());
        int id = (int)sqlite.insert(Contrato.TablaPartido.TABLA, null, cv);
        return id;
    }

    public int delete(Partido p) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { p.getId() + "" };
        int cuenta = sqlite.delete( Contrato.TablaPartido.TABLA, condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select(String condicion) {
        List<Partido> partidos = new ArrayList<Partido>();
        Cursor cursor = sqlite.query(Contrato.TablaPartido.TABLA, null,
                condicion, null, null, null, null);
        cursor.moveToFirst();
        Partido p;
        while (!cursor.isAfterLast()) {
            p = getRow(cursor);
            partidos.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return partidos;
    }

    public Partido getRow(Cursor c) {
        Partido p = new Partido();
        p.setId(c.getInt(0));
        p.setIdjugador(c.getInt(1));
        p.setContrincante(c.getString(2));
        p.setValoracion(c.getInt(3));
        return p;
    }

    public Partido getRow(int id){
        List<Partido> partidos=select(Contrato.TablaPartido._ID + " = " +id);
        if(!partidos.isEmpty())
            return partidos.get(0);
        return null;
    }

    public Cursor getCursor(String condicion,String[] parametros, String orden) {
        Cursor cursor= sqlite.query(Contrato.TablaPartido.TABLA, null, condicion, parametros,null, null,orden);
        return cursor;
    }
}