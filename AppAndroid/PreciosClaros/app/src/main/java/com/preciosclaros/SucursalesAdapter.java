package com.preciosclaros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by lucas on 15/6/2017.
 */

public class SucursalesAdapter extends ArrayAdapter<Sucursales> {
    public SucursalesAdapter(Context context, ArrayList<Sucursales> sucursales) {
        super(context, 0, sucursales);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sucursales sucursal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.producto_sucursal, parent, false);
        }
        // Lookup view for data population
        TextView distancia = (TextView) convertView.findViewById(R.id.distancia);
        TextView nombreComercio = (TextView) convertView.findViewById(R.id.nombreComercio);
        TextView direccionComercio = (TextView) convertView.findViewById(R.id.direccion);
        TextView precioComercio = (TextView) convertView.findViewById(R.id.precio);
        TextView localidad = (TextView) convertView.findViewById(R.id.localidad);
        ImageView imgComercio = (ImageView) convertView.findViewById(R.id.imgComercio);
        // Populate the data into the template view using the data object
        distancia.setText(sucursal.getDistanciaDescripcion());
        nombreComercio.setText(sucursal.getBanderaDescripcion());
        direccionComercio.setText(sucursal.getDireccion());
        localidad.setText(sucursal.getLocalidad());
        Picasso.with(getContext()).load("https://imagenes.preciosclaros.gob.ar/comercios/"+sucursal.getComercioId()+"-1.jpg").into(imgComercio);
       // Double pre = sucursal.getPreciosProducto().getPrecioLista();
        BigDecimal bg = new BigDecimal(10.50);
        precioComercio.setText(bg.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
