package com.izv.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Secundaria extends Activity {

    private GestorPartido gp;
    private AdaptadorPartido ap;
    private ListView lvp;
    private TextView tv;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_secundaria);

        lvp=(ListView) findViewById(R.id.lvPartidos);
        gp=new GestorPartido(this);

        Bundle b = getIntent().getExtras();
        i=b.getInt("id");

        tv=(TextView)findViewById(R.id.jugador);
        tv.setText("Jugador: "+i);

        verPartidos(i);

    }

    public void verPartidos(final int jugador){
        gp.open();

        Cursor c = gp.getCursor(" IDJugador = " + jugador,null,null);
        ap = new AdaptadorPartido(this, c);
        lvp.setAdapter(ap);
        lvp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Cursor c = (Cursor) lvp.getItemAtPosition(pos);
                Partido p = gp.getRow(c);
                gp.delete(p);

                ap.changeCursor(gp.getCursor(" IDJugador = " + jugador, null, null));
                return false;
            }
        });
    }

    public void añadirPartido(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Añadir Partido");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_partido, null);
        alert.setView(vista);
        alert.setPositiveButton("Insertar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et = (EditText)vista.findViewById(R.id.contrincante);

                        Partido p = new Partido(0,
                                i,
                                et.getText().toString(),
                                Integer.parseInt((((EditText)vista.findViewById(R.id.valoracion)).getText().toString()).trim()));
                        tostada("Elemento añadido:" + p.toString());
                        gp.insert(p);
                        ap.notifyDataSetChanged();
                        verPartidos(i);
                    }
                });
        alert.setNegativeButton("cancelar", null);
        alert.show();
    }

    public void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
