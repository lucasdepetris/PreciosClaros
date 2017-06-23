package com.preciosclaros.modelo;

import java.util.ArrayList;

/**
 * Created by lucas on 22/6/2017.
 */

public class Items {
    private Comercio Comercio;
    private ArrayList<Productos> Productos;

    public Items(com.preciosclaros.modelo.Comercio comercio, ArrayList<com.preciosclaros.modelo.Productos> productos) {
        Comercio = comercio;
        Productos = productos;
    }

    public com.preciosclaros.modelo.Comercio getComercio() {

        return Comercio;
    }

    public void setComercio(com.preciosclaros.modelo.Comercio comercio) {
        Comercio = comercio;
    }

    public ArrayList<com.preciosclaros.modelo.Productos> getProductos() {
        return Productos;
    }

    public void setProductos(ArrayList<com.preciosclaros.modelo.Productos> productos) {
        Productos = productos;
    }
}
