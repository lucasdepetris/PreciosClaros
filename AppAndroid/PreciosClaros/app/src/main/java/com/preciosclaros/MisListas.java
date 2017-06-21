package com.preciosclaros;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.preciosclaros.adaptadores.ListasAdaptador;
import com.preciosclaros.modelo.Lista;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucas on 19/6/2017.
 */

public class MisListas extends AppCompatActivity {

    private PopupWindow pw;
    public Button Close,CrearLista;
    @OnClick(R.id.AgregarLista)public void AgregarLista(){
        showPopup();
    }
    @BindView(R.id.recyclerListas)
    RecyclerView recyclerView;
    private EditText nombre ;
    private EditText descripcion ;
    public final String TAG = "";
    public Context ctx = this;
    ApiPrecios service;
    public Call<ArrayList<Lista>> requestCatalog;
    private static final String PREFER_NAME = "Reg";
    private SharedPreferences sharedPreferences;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_listas);
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
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        requestCatalog = service.getListas(sharedPreferences.getInt("id",id));
        requestCatalog.enqueue(new Callback<ArrayList<Lista>>() {
            @Override
            public void onResponse(Call<ArrayList<Lista>> call, retrofit2.Response<ArrayList<Lista>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Lista> listas = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    ListasAdaptador adapter = new ListasAdaptador(listas);
                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recyclerView.setAdapter(adapter);
                    Log.i(TAG, "Artículo descargado: ");
                } else {
                    int code = response.code();
                    String c = String.valueOf(code);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Lista>> call, Throwable t) {
                Log.e(TAG, "Error:" + t.getCause());

            }

        });

    }
    public void showPopup(){
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.agregar_lista,
                    (ViewGroup) findViewById(R.id.agregar_popup));
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            nombre = (EditText) layout.findViewById(R.id.NombreListaNueva);
            descripcion = (EditText) layout.findViewById(R.id.DescripcionListaNueva);
            Close = (Button) layout.findViewById(R.id.btnCerrarPopup);
            Close.setOnClickListener(cancel_button);
            CrearLista = (Button) layout.findViewById(R.id.btnAgregarLista);
            CrearLista.setOnClickListener(crear_lista);
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
            sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
            Call<Lista> requestCrear = service.putLista(sharedPreferences.getInt("id",id),nombre.getText().toString(),descripcion.getText().toString());
            requestCrear.enqueue(new Callback<Lista>() {
                @Override
                public void onResponse(Call<Lista> call, retrofit2.Response<Lista> response) {
                    if(response.isSuccessful()){
                        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
                        requestCatalog = service.getListas(sharedPreferences.getInt("id",id));
                        requestCatalog.enqueue(new Callback<ArrayList<Lista>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Lista>> call, retrofit2.Response<ArrayList<Lista>> response) {
                                if (response.isSuccessful()) {
                                    ArrayList<Lista> listas = response.body();
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    ListasAdaptador adapter = new ListasAdaptador(listas);
                                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                                    recyclerView.setAdapter(adapter);
                                    Log.i(TAG, "Artículo descargado: ");
                                } else {
                                    int code = response.code();
                                    String c = String.valueOf(code);
                                }


                            }

                            @Override
                            public void onFailure(Call<ArrayList<Lista>> call, Throwable t) {
                                Log.e(TAG, "Error:" + t.getCause());

                            }

                        });
                        pw.dismiss();
                    }
                    else {
                        //SI NO CREA CORRECTAMENTE LA LISTA HACEMOS ESTO
                    }
                }

                @Override
                public void onFailure(Call<Lista> call, Throwable t) {

                }
            });

        }
    };
}