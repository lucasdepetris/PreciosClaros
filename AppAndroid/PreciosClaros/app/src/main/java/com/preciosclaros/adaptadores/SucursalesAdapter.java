package com.preciosclaros.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.preciosclaros.R;
import com.preciosclaros.modelo.Sucursales;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 15/6/2017.
 */
public class SucursalesAdapter extends RecyclerView.Adapter<SucursalesAdapter.ViewHolder> {

    private List<Sucursales> sucursales = new ArrayList<Sucursales>();

    public SucursalesAdapter(List<Sucursales> sucursales) {

        this.sucursales = sucursales;
    }
    public List<Sucursales> getSucursales(){return this.sucursales;}

    @Override
    public int getItemCount() {
        return sucursales.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_sucursal, null);
        itemLayoutView.findViewById(R.id.agregar).setOnClickListener(mOnClickListener);
        return new SucursalesAdapter.ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Sucursales sucursal = this.sucursales.get(position);
        holder.distancia.setText(sucursal.getDistanciaDescripcion());
        holder.nombreComercio.setText(sucursal.getBanderaDescripcion());
        holder.direccion.setText(sucursal.getDireccion());
        holder.precio.setText("$"+sucursal.getPreciosProducto().getPrecioLista());
        holder.localidad.setText(sucursal.getLocalidad());
        Picasso.with(holder.imgComercio.getContext()).load("https://imagenes.preciosclaros.gob.ar/comercios/"+sucursal.getComercioId()+"-1.jpg").into(holder.imgComercio);

    }
    public final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            
            List<Sucursales> sucs = getSucursales();
            Toast.makeText(view.getContext(),"hola", Toast.LENGTH_LONG).show();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View item;
       /* @OnClick(R.id.agregar)public void agregarProd(){

            }*/
        @BindView(R.id.distancia)
        TextView distancia;
        @BindView(R.id.nombreComercio)
        TextView nombreComercio;
        @BindView(R.id.direccion)
        TextView direccion;
        @BindView(R.id.precio)
        TextView precio;
        @BindView(R.id.localidad)
        TextView localidad;
        @BindView(R.id.imgComercio)
        ImageView imgComercio;



        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            ButterKnife.bind(this, itemView);
        }

    }
}
/*
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
        precioComercio.setText(sucursal.getPreciosProducto().getPrecioLista());
        distancia.setText(sucursal.getDistanciaDescripcion());
        nombreComercio.setText(sucursal.getBanderaDescripcion());
        direccionComercio.setText(sucursal.getDireccion());
        localidad.setText(sucursal.getLocalidad());
        Picasso.with(getContext()).load("https://imagenes.preciosclaros.gob.ar/comercios/"+sucursal.getComercioId()+"-1.jpg").into(imgComercio);
       // Double pre = sucursal.getPreciosProducto().getPrecioLista();
        // Return the completed view to render on screen
        return convertView;
    }
}*/
