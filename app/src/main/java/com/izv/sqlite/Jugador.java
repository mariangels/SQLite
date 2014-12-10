package com.izv.sqlite;

import java.io.Serializable;

/** Java beans POJO*/

public class Jugador implements Serializable, Comparable<Jugador>{

    private int id;
    private String nombre;
    private String telefono;
    private String fnac;

    //1. constructor vacio
    //2. constructor completo
    //3. getter setter
    //4. equals, hascode -> clave principal de la tabla
    //5. compare to
    //6. to string

    public Jugador() {
        this(0,"","","");
    }

    public Jugador(int id, String nombre, String telefono, String fnac) {
        this.id = id;
        this.fnac = fnac;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fnac='" + fnac + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Jugador jugador) {
        //jugadores -> this, jugador
        /*if(this.valoracion != jugador.valoracion){
            return this.valoracion-jugador.valoracion;
        }else */
        if(this.nombre.compareTo(jugador.nombre)!=0){
            return this.nombre.compareTo(jugador.nombre);
        }else{
            return this.fnac.compareTo(jugador.fnac);
        }
    }


}
