package com.izv.sqlite;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorPartido extends CursorAdapter {


    public AdaptadorPartido(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.detalle_partido, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GestorPartido gp=new GestorPartido(context);
        Partido p;
        p=gp.getRow(cursor);
        ((TextView) view.findViewById(R.id.contr)).setText(p.getContrincante().toString());
        ((TextView) view.findViewById(R.id.val)).setText(p.getValoracion()+"");
    }
}

