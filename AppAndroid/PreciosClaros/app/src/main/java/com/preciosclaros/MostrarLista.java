package com.preciosclaros;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.preciosclaros.adaptadores.ListasAdaptador;
import com.preciosclaros.modelo.Lista;

import java.util.ArrayList;

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
    public Call<Lista> requestCatalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_productos);
        ButterKnife.bind(this);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://maprecios.azurewebsites.net/")
                .build();
        service = retrofit.create(ApiPrecios.class);

        requestCatalog = service.getLista(9);
        requestCatalog.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(Call<Lista> call, retrofit2.Response<Lista> response) {
                if (response.isSuccessful()) {
                    Lista lista = response.body();
                    /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    ListasAdaptador adapter = new ListasAdaptador(listas);
                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recyclerView.setAdapter(adapter);
                    Log.i(TAG, "Artículo descargado: ");*/
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
