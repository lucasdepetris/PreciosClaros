package com.preciosclaros.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.preciosclaros.modelo.Producto;
import com.preciosclaros.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 22/6/2017.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    private List<Producto> productos = new ArrayList<Producto>();

    public ProductosAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos, null);
        return new ProductosAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ProductosAdapter.ViewHolder holder, int position) {
        Producto producto = this.productos.get(position);
        holder.precioProducto.setText("$"+"precio");
        holder.descripcionProducto.setText(producto.getNombre());
        Picasso.with(holder.imgProducto.getContext()).load("https://imagenes.preciosclaros.gob.ar/productos/"+producto.getId()+".jpg")
                .placeholder(R.drawable.image_placeholder).error(R.drawable.no_image_aivalable).into(holder.imgProducto);
       // Picasso.with(holder.imgProducto.getContext()).load("https://imagenes.preciosclaros.gob.ar/comercios/"+sucursal.getComercioId()+"-1.jpg").into(holder.imgComercio);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View item;

        @BindView(R.id.imgProducto)
        ImageView imgProducto;
        @BindView(R.id.precioProducto) TextView precioProducto;
        @BindView(R.id.descripcionProducto) TextView descripcionProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}