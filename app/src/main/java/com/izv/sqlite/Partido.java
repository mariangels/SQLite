package com.izv.sqlite;

import java.io.Serializable;

public class Partido implements Serializable, Comparable<Partido>{


    private int id;
    private int idjugador;
    private String contrincante;
    private int valoracion;

    public Partido() {
    }

    public Partido(int id, int idjugador, String contrincante, int valoracion) {
        this.id = id;
        this.idjugador = idjugador;
        this.contrincante = contrincante;
        this.valoracion = valoracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", idjugador=" + idjugador +
                ", contrincante='" + contrincante + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partido)) return false;

        Partido partido = (Partido) o;

        if (id != partido.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Partido partido) {
        //jugadores -> this, jugador
        if(this.valoracion != partido.valoracion){
            return this.valoracion- partido.valoracion;
        }
        else if(this.idjugador != partido.idjugador){
            return this.idjugador- partido.idjugador;
        }else{
            return this.contrincante.compareTo(partido.contrincante);
        }

    }


    //1. constructor vacio
    //2. constructor completo
    //3. getter setter
    //4. equals, hascode -> clave principal de la tabla
    //5. compare to
    //6. to string








}
