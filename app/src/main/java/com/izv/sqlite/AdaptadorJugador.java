package com.izv.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorJugador extends CursorAdapter{


    public AdaptadorJugador(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.detalle_jugador, vg, false);
        return v;
    }

    //patrón viewholder
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GestorJugador gj=new GestorJugador(context);
        Jugador j;
        j=gj.getRow(cursor);

        ((TextView) view.findViewById(R.id.tv1)).setText(j.getNombre().toString());
        ((TextView) view.findViewById(R.id.tv2)).setText(j.getTelefono().toString());
        ((TextView) view.findViewById(R.id.tv3)).setText(j.getFnac().toString());

/*
        String media="";
        String condicion= Contrato.TablaPartido.IDJUGADOR + " = " + j.getId();
        List<Partido> partidos=gp.select(condicion);
        partidos.size();
        int suma=0;
        int i;
        for(i=0; i<partidos.size() ;i++){
            suma+=partidos.get(i).getValoracion();
        }
        media=(suma/i) +"";
*/
        gj.openRead();
        ((TextView) view.findViewById(R.id.tv4)).setText(gj.valoracion(j.getId())+"");
    }

}
