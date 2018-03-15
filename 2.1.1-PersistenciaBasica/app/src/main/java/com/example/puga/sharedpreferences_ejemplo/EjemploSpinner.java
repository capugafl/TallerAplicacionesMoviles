package com.example.puga.sharedpreferences_ejemplo;

/**
 * Created by diego.lira on 29/04/2017.
 */

public class EjemploSpinner {
    int id;
    String nombre;

    public EjemploSpinner(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
