package com.preciosclaros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.preciosclaros.adaptadores.MiListaAdaptador;
import com.preciosclaros.modelo.Items;
import com.preciosclaros.modelo.Lista;
import com.preciosclaros.modelo.Listas;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.ReciclerContenidoLista) RecyclerView recicler;
    public Call<Lista> requestCatalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_lista);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int i = 0;
        int id = intent.getIntExtra("idLista",i);
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
                    MiListaAdaptador adapter = new MiListaAdaptador(items);
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
}
