package com.preciosclaros;

import java.util.ArrayList;

/**
 * Created by lucas on 11/6/2017.
 */

public class Response {

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    Producto producto;
    ArrayList<Sucursales> sucursales = new ArrayList<Sucursales>();
    public Response(Producto producto, ArrayList<Sucursales> sucursales , int status) {
        this.producto = producto;
        this.sucursales = sucursales;
        this.status = status;
    }

    public Producto getProducto() {

        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ArrayList<Sucursales> getProductos() {
        return sucursales;
    }

    public void setProductos(ArrayList<Sucursales> productos) {
        this.sucursales = productos;
    }

}
