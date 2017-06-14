package com.preciosclaros;

import java.util.ArrayList;

/**
 * Created by lucas on 11/6/2017.
 */

public class Response {
    Producto producto;
    ArrayList<Sucursale> productos = new ArrayList<Sucursale>();
    public Response(Producto producto, ArrayList<Sucursale> productos) {
        this.producto = producto;
        this.productos = productos;
    }

    public Producto getProducto() {

        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ArrayList<Sucursale> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Sucursale> productos) {
        this.productos = productos;
    }

}
