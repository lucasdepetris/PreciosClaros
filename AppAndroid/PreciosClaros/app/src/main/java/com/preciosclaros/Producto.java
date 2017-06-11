package com.preciosclaros;

/**
 * Created by lucas on 11/6/2017.
 */

public class Producto {
    private double presentacion;
    private String nombre;
    private Integer id;
    private String marca;

    public double getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(double presentacion) {
        this.presentacion = presentacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
