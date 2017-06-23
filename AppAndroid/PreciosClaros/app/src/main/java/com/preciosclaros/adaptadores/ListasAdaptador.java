package com.preciosclaros.adaptadores;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.preciosclaros.MostrarLista;
import com.preciosclaros.R;
import com.preciosclaros.modelo.Listas;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 19/6/2017.
 */

public class ListasAdaptador extends RecyclerView.Adapter<ListasAdaptador.ViewHolder> {

    private List<Listas> listas = new ArrayList<Listas>();

    public ListasAdaptador(List<Listas> listas) {
        this.listas = listas;
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    @Override
    public ListasAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_lista, null);

        return new ListasAdaptador.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Listas lista = this.listas.get(position);
        holder.NombreLista.setText(lista.getNombre());
        holder.Descripcion.setText(lista.getDescripcion());
        holder.imgLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MostrarLista.class);
                intent.putExtra("idLista",lista.getId());
                v.getContext().startActivity(intent);
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View item;

        @BindView(R.id.NombreLista)
        TextView NombreLista;
        @BindView(R.id.DescripcionLista) TextView Descripcion;
        @BindView(R.id.imgLista)
        ImageView imgLista;
        public ViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}