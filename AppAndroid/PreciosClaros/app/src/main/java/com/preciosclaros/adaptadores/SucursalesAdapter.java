package com.preciosclaros.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
    private int Position;
   /* private PopupWindow popupWindow;
    EditText cantidad;
    Spinner spinner;
    public Button Close,CrearProd;
    public void showPopup(View view) {
        try {
            final View popupView = LayoutInflater.from(view.getContext().getApplicationContext()).inflate(R.layout.agregar_producto,
                    (ViewGroup) view.findViewById(R.id.agregar_prod));
            popupWindow = new PopupWindow(popupView, 500, 500);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            cantidad = (EditText) popupView.findViewById(R.id.Cantidad);
            Close = (Button) popupView.findViewById(R.id.btnCerrarProducto);
            Close.setOnClickListener(cancel_button);
            CrearProd = (Button) popupView.findViewById(R.id.btnAgregarProducto);
            CrearProd.setOnClickListener(agregar_producto_lista);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {

            popupWindow.dismiss();
        }
    };
    private View.OnClickListener agregar_producto_lista = new View.OnClickListener() {
        public void onClick(View v) {

            popupWindow.dismiss();
        }
    };*/
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
        return new SucursalesAdapter.ViewHolder(itemLayoutView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Sucursales sucursal = this.sucursales.get(position);
        holder.distancia.setText(sucursal.getDistanciaDescripcion());
        holder.nombreComercio.setText(sucursal.getBanderaDescripcion());
        holder.direccion.setText(sucursal.getDireccion());
        holder.precio.setText("$"+sucursal.getPreciosProducto().getPrecioLista());
        holder.localidad.setText(sucursal.getLocalidad());
        Picasso.with(holder.imgComercio.getContext()).load("https://imagenes.preciosclaros.gob.ar/comercios/"+sucursal.getComercioId()+"-1.jpg")
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.no_image_aivalable)
                .into(holder.imgComercio);
        holder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"hola"+sucursal.getBanderaDescripcion(), Toast.LENGTH_LONG).show();
              //  showPopup(v);

            }
        });

    }

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
        @BindView(R.id.agregar)
        ImageButton agregar;


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
