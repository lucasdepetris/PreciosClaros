package com.preciosclaros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.preciosclaros.adaptadores.ListasAdaptador;
import com.preciosclaros.adaptadores.MiListaAdaptador;
import com.preciosclaros.modelo.Items;
import com.preciosclaros.modelo.Lista;
import com.preciosclaros.modelo.Listas;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucas on 22/6/2017.
 */

public class MostrarLista extends AppCompatActivity {
    public Context ctx = this;
    ApiPrecios service;
    private PopupWindow pw;
    public Button Close,Aceptar;
    private EditText cantidad ;
    private String idArticulo;
    private int id;
    private int CantidadAnt;
    //@OnClick(R.id.cantidadProductoMiLista)public void cambiarCantidad() {showPopup();}
    @BindView(R.id.ReciclerContenidoLista) RecyclerView recicler;
    public Call<Lista> requestCatalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_lista);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int i = 0;
        id = intent.getIntExtra("idLista",i);
        Toast.makeText(this,"hola"+id,Toast.LENGTH_SHORT).show();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://maprecios.azurewebsites.net/")
                .build();
        service = retrofit.create(ApiPrecios.class);

        requestCatalog = service.getLista(id);
        requestCatalog.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(Call<Lista> call, retrofit2.Response<Lista> response) {
                if (response.isSuccessful()) {
                    Lista lista = response.body();
                    ArrayList<Items> items = lista.getItems();
                    TextView txt = (TextView) findViewById(R.id.VerNombreLista);
                    TextView txt2 = (TextView) findViewById(R.id.VerDescripcionLista);
                    txt.setText(lista.getNombre());
                    txt2.setText(lista.getDescripcion());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                    recicler.setLayoutManager(linearLayoutManager);
                    recicler.addItemDecoration(new SimpleDividerItemDecoration(
                            getApplicationContext()
                    ));
                    MiListaAdaptador adapter = new MiListaAdaptador(items,ctx);
                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recicler.setAdapter(adapter);
                    String TAG = null;
                    Log.i(TAG, "Artículo descargado: ");
               } else {
                    int code = response.code();
                    String c = String.valueOf(code);
                }


            }

            @Override
            public void onFailure(Call<Lista> call, Throwable t) {
                String TAG = null;
                Log.e(TAG, "Error:" + t.getCause());

            }

        });
    }
    public void showPopup(String cantidadAnt, String idArticulo){
        try {
// We need to get the instance of the LayoutInflater
            this.idArticulo = idArticulo;
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.modificar_cantidad_producto,
                    (ViewGroup) findViewById(R.id.mod_cant));
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            cantidad = (EditText) layout.findViewById(R.id.Cantidad);
            Close = (Button) layout.findViewById(R.id.btnCerrarCantidad);
            Close.setOnClickListener(cancel_button);
            Aceptar = (Button) layout.findViewById(R.id.btnModificarCantidad);
            Aceptar.setOnClickListener(crear_lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private View.OnClickListener crear_lista = new View.OnClickListener() {
        public void onClick(View v) {
            int c = Integer.parseInt(cantidad.getText().toString());
            requestCatalog = service.modificarCantidad(id,idArticulo,c);
            requestCatalog.enqueue(new Callback<Lista>() {
                @Override
                public void onResponse(Call<Lista> call, retrofit2.Response<Lista> response) {
                    if (response.isSuccessful()) {
                        Lista lista = response.body();
                        ArrayList<Items> items = lista.getItems();
                        TextView txt = (TextView) findViewById(R.id.VerNombreLista);
                        TextView txt2 = (TextView) findViewById(R.id.VerDescripcionLista);
                        txt.setText(lista.getNombre());
                        txt2.setText(lista.getDescripcion());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                        recicler.setLayoutManager(linearLayoutManager);
                        recicler.addItemDecoration(new SimpleDividerItemDecoration(
                                getApplicationContext()
                        ));
                        MiListaAdaptador adapter = new MiListaAdaptador(items,ctx);
                        // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                        recicler.setAdapter(adapter);
                        String TAG = null;
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<Lista> call, Throwable t) {
                    String TAG = null;
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
            pw.dismiss();
        }
    };
    public void showPopupEliminarProducto(String idArticulo){
        try {
// We need to get the instance of the LayoutInflater
            this.idArticulo = idArticulo;
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.eliminar,
                    (ViewGroup) findViewById(R.id.eliminar_item));
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Close = (Button) layout.findViewById(R.id.btnCerrarEliminar);
            Close.setOnClickListener(cancel_button_eliminar_producto);
            Aceptar = (Button) layout.findViewById(R.id.btnEliminar);
            Aceptar.setOnClickListener(eliminar_producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_eliminar_producto = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private View.OnClickListener eliminar_producto = new View.OnClickListener() {
        public void onClick(View v) {

            requestCatalog = service.EliminarProducto(idArticulo,id);
            requestCatalog.enqueue(new Callback<Lista>() {
                @Override
                public void onResponse(Call<Lista> call, retrofit2.Response<Lista> response) {
                    if (response.isSuccessful()) {
                        Lista lista = response.body();
                        ArrayList<Items> items = lista.getItems();
                        TextView txt = (TextView) findViewById(R.id.VerNombreLista);
                        TextView txt2 = (TextView) findViewById(R.id.VerDescripcionLista);
                        txt.setText(lista.getNombre());
                        txt2.setText(lista.getDescripcion());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                        recicler.setLayoutManager(linearLayoutManager);
                        recicler.addItemDecoration(new SimpleDividerItemDecoration(
                                getApplicationContext()
                        ));
                        MiListaAdaptador adapter = new MiListaAdaptador(items,ctx);
                        // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                        recicler.setAdapter(adapter);
                        String TAG = null;
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<Lista> call, Throwable t) {
                    String TAG = null;
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
            pw.dismiss();
        }
    };
}
