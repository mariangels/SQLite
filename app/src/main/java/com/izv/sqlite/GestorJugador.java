package com.izv.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GestorJugador {

    private Ayudante ayudante;
    private SQLiteDatabase sqlite;

    public GestorJugador(Context c) {
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

    public int insert(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        //valores.put(Contrato.TablaJugador.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaJugador.FNAC, objeto.getFnac());
        // id es el codigo autonumerico

        int id = (int)sqlite.insert(Contrato.TablaJugador.TABLA, null, valores);
        return id;
    }

    public int delete(Jugador objeto) {
        String condicion = Contrato.TablaJugador._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = sqlite.delete( Contrato.TablaJugador.TABLA, condicion,argumentos);
        //delete from jugador where id=?
        return cuenta;
    }
    //creaamos una sentencia preparada: por si sola no se ejecuta y mas tarde le pasamos los parametros
    //si solo lo vamos a utilizar una vez es mas lenta, pero en general es mas rapida
    //es mas comoda
    //es segura

    public int deletePartidos(int i) {
        String condicion = Contrato.TablaPartido.IDJUGADOR + " = " + i;
        int cuenta = sqlite.delete( Contrato.TablaPartido.TABLA, condicion, null);
        return cuenta;
    }

    //no vamos a editar
/*
    public int update(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugador.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaJugador.FNAC, objeto.getFnac());
        String condicion = Contrato.TablaJugador._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = sqlite.update(Contrato.TablaJugador.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }
*/
    public List<Jugador> select(String condicion) {
        List<Jugador> jugadores = new ArrayList<Jugador>();
        //CURSOR: elemento que me devuelve un elemento que me permite recorrer la
        // respuesta de un select
        Cursor cursor = sqlite.query(Contrato.TablaJugador.TABLA, null,condicion, null, null, null, null);
        //select *from jugador where condicion
        cursor.moveToFirst();
        Jugador objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            jugadores.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return jugadores;
    }

    //Obtiene un jugador por el cursor
    public Jugador getRow(Cursor c) {
        Jugador objeto = new Jugador();
        /*.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0*/

        objeto.setId(c.getInt(0));
        objeto.setNombre(c.getString(1));
        objeto.setTelefono(c.getString(2));
        //objeto.setValoracion(c.getInt(3));
        objeto.setFnac(c.getString(3));
        return objeto;
    }

    //Consulta donde obtiene al jugador por el id
    /*public Jugador getRow(int id){
        List<Jugador> lj=select(Contrato.TablaJugador._ID + " = "+ id);
        if(!lj.isEmpty())
            return lj.get(0);
        return null;
    }*/

    public Cursor getCursor(String condicion,String[] parametros, String orden) {
        Cursor cursor= sqlite.query(Contrato.TablaJugador.TABLA, null, condicion, parametros,null, null,orden);
        return cursor;
    }

    public long valoracion(int jugador){
        String condicion=Contrato.TablaPartido.IDJUGADOR + " = ? ";
        String[] parametros=new String[]{jugador+""};
        String[] select=new String[]{ Contrato.TablaPartido.IDJUGADOR+ ", avg(" + Contrato.TablaPartido.VALORACION +") "};
        //Cursor c= sqlite.query( Contrato.TablaPartido.TABLA, select, condicion, parametros, Contrato.TablaPartido.IDJUGADOR, null, null);
        //Cursor c= sqlite.query(Contrato.TablaPartido.TABLA, null, null, null, null, null,null);
        Cursor c= sqlite.query(Contrato.TablaPartido.TABLA, select, condicion, parametros, null, null,null);

        c.moveToFirst();
        //select avg(valoracion) from partido where idJugador=? groupby idJugador
        return c.getLong(1);
    }
}