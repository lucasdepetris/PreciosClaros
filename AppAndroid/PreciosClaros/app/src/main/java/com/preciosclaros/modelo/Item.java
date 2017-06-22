package com.preciosclaros.modelo;

import java.util.ArrayList;

/**
 * Created by lucas on 22/6/2017.
 */

public class Item {
    private Comercio Comercio;
    private ArrayList<Productos> Productos;

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
