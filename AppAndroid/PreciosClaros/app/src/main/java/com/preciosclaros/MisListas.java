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
import com.preciosclaros.modelo.Listas;

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
    public Call<ArrayList<Listas>> requestCatalog;
    private static final String PREFER_NAME = "Reg";
    private SharedPreferences sharedPreferences;
    int id;
    Listas listaEditar;
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
        requestCatalog.enqueue(new Callback<ArrayList<Listas>>() {
            @Override
            public void onResponse(Call<ArrayList<Listas>> call, retrofit2.Response<ArrayList<Listas>> response) {
                if (response.isSuccessful()) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    ArrayList<Listas> listas = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    ListasAdaptador adapter = new ListasAdaptador(listas,ctx);
                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recyclerView.setAdapter(adapter);
                    Log.i(TAG, "Artículo descargado: ");
                } else {
                    int code = response.code();
                    String c = String.valueOf(code);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
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
            pw = new PopupWindow(layout, 900,600, true);
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
            Call<Listas> requestCrear = service.putLista(sharedPreferences.getInt("id",id),nombre.getText().toString(),descripcion.getText().toString());
            requestCrear.enqueue(new Callback<Listas>() {
                @Override
                public void onResponse(Call<Listas> call, retrofit2.Response<Listas> response) {
                    if(response.isSuccessful()){
                        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
                        requestCatalog = service.getListas(sharedPreferences.getInt("id",id));
                        requestCatalog.enqueue(new Callback<ArrayList<Listas>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Listas>> call, retrofit2.Response<ArrayList<Listas>> response) {
                                if (response.isSuccessful()) {
                                    ArrayList<Listas> listas = response.body();
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    ListasAdaptador adapter = new ListasAdaptador(listas,ctx);
                                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                                    recyclerView.setAdapter(adapter);
                                    Log.i(TAG, "Artículo descargado: ");
                                } else {
                                    int code = response.code();
                                    String c = String.valueOf(code);
                                }


                            }

                            @Override
                            public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
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
                public void onFailure(Call<Listas> call, Throwable t) {

                }
            });

        }
    };
    public void showPopupEditarLista(Listas lista){
        try {
// We need to get the instance of the LayoutInflater
            listaEditar = lista;
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.agregar_lista,
                    (ViewGroup) findViewById(R.id.agregar_popup));
            pw = new PopupWindow(layout, 900,600, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            nombre = (EditText) layout.findViewById(R.id.NombreListaNueva);
            descripcion = (EditText) layout.findViewById(R.id.DescripcionListaNueva);
            Close = (Button) layout.findViewById(R.id.btnCerrarPopup);
            Close.setOnClickListener(cancel_button_editar);
            CrearLista = (Button) layout.findViewById(R.id.btnAgregarLista);
            CrearLista.setText("MODIFICAR");
            CrearLista.setOnClickListener(crear_lista_editar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_editar = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private View.OnClickListener crear_lista_editar = new View.OnClickListener() {
        public void onClick(View v) {
            sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
            requestCatalog = service.modificarLista(listaEditar.getId(),nombre.getText().toString(),descripcion.getText().toString(),sharedPreferences.getInt("id",id));
            requestCatalog.enqueue(new Callback<ArrayList<Listas>>() {
                @Override
                public void onResponse(Call<ArrayList<Listas>> call, retrofit2.Response<ArrayList<Listas>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Listas> listas = response.body();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        ListasAdaptador adapter = new ListasAdaptador(listas,ctx);
                        // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                        recyclerView.setAdapter(adapter);
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
            pw.dismiss();
        }
    };
    public void showPopupEliminarLista(Listas lista){
        try {
            listaEditar = lista;
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.eliminar,
                    (ViewGroup) findViewById(R.id.eliminar_item));
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Close = (Button) layout.findViewById(R.id.btnCerrarEliminar);
            Close.setOnClickListener(cancel_button_eliminar);
            CrearLista = (Button) layout.findViewById(R.id.btnEliminar);
            CrearLista.setOnClickListener(crear_lista_eliminar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_eliminar = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private View.OnClickListener crear_lista_eliminar = new View.OnClickListener() {
        public void onClick(View v) {
            sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
            requestCatalog = service.eliminarLista(listaEditar.getId(),sharedPreferences.getInt("id",id));
            requestCatalog.enqueue(new Callback<ArrayList<Listas>>() {
                @Override
                public void onResponse(Call<ArrayList<Listas>> call, retrofit2.Response<ArrayList<Listas>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Listas> listas = response.body();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        ListasAdaptador adapter = new ListasAdaptador(listas,ctx);
                        // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                        recyclerView.setAdapter(adapter);
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
        pw.dismiss();
        }
    };

}
