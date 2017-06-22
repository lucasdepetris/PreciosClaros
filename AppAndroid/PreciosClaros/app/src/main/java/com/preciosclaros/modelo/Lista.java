package com.preciosclaros.modelo;

import java.util.ArrayList;

/**
 * Created by lucas on 19/6/2017.
 */

public class Lista {
    private int id;
    private String Nombre;
    private String Descripcion;
    private ArrayList<Item> Items;

    public ArrayList<Item> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }

    public Lista(int id, String nombre, String descripcion) {
        this.id = id;
        Nombre = nombre;
        Descripcion = descripcion;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
