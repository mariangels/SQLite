package com.izv.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



public class Principal extends FragmentActivity {

    private GestorJugador gj;
    private AdaptadorJugador aj;
    private ListView lvj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad);

        lvj=(ListView) findViewById(R.id.lvJugadores);
        gj=new GestorJugador(this);

        verJugadores();
    }

    public void verJugadores(){
        gj.open();
        Cursor c = gj.getCursor(null,null,null);
        aj = new AdaptadorJugador(this, c);
        lvj=(ListView) findViewById(R.id.lvJugadores);
        lvj.setAdapter(aj);

        final Intent i = new Intent(this,Secundaria.class);
        lvj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Cursor c=(Cursor)lvj.getItemAtPosition(pos);
                Jugador j=gj.getRow(c);
                gj.delete(j);
                gj.deletePartidos(j.getId());
                aj.changeCursor(gj.getCursor(null,null,null));
                return false;
            }
        });
        lvj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Bundle b=new Bundle();
                Cursor c=(Cursor)lvj.getItemAtPosition(pos);
                Jugador j=gj.getRow(c);
                b.putInt("id", j.getId());//el id
                i.putExtras(b);
                startActivityForResult(i, 1);
            }
        });
    }


    public void añadirJugador(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Añadir Jugador");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_jugador, null);
        alert.setView(vista);
        alert.setPositiveButton("insertar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Jugador j = new Jugador(0,
                                ((EditText)vista.findViewById(R.id.nombre)).getText().toString(),
                                ((EditText)vista.findViewById(R.id.telefono)).getText().toString(),
                                ((EditText)vista.findViewById(R.id.fnac)).getText().toString());
                        tostada("Elemento añadido: "+j.toString());
                        gj.insert(j);
                        aj.notifyDataSetChanged();
                        verJugadores();
                    }
                });
        alert.setNegativeButton("cancelar", null);
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode,  int resultCode, Intent data){
        verJugadores();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }

    @Override
    protected void onResume() {
        verJugadores();
        super.onResume();
    }


    public void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

/*
TRATAMIENTODELASEXCEPCIONES

try {
… // operación sobre la base de datos
} catch (android.database.sqlite.SQLiteConstraintException e){
…
} catch(android.database.sqlite.SQLiteException e){
…
}*/

}
