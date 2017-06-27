package com.preciosclaros;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.preciosclaros.adaptadores.SucursalesAdapter;
import com.preciosclaros.modelo.Listas;
import com.preciosclaros.modelo.Producto;
import com.preciosclaros.modelo.Response;
import com.preciosclaros.modelo.Sucursales;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
 * Created by lucas on 23/6/2017.
 */

public class VerProductoPorId extends AppCompatActivity {
    public final String TAG = "";
    //View Objects
    private PopupWindow pw;
    EditText cantidad;
    Spinner spinner;
    public Button Close,CrearProd;
    public Sucursales sucursalElegida;
    @BindView(R.id.MejorNombre)
    TextView nombreProducto;
    @BindView(R.id.MejorPrecio) TextView precioProducto;
    @BindView(R.id.MejorImgProducto)
    ImageView imgProducto;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    Producto mejorProducto;
    Sucursales mejorSucursal;
    ArrayList<Listas> ls;
    public String[] select;
    ArrayList<String> ListasId;
    private IntentIntegrator qrScan;
    public String id;
    ApiPrecios service;
    public Call<Response> requestCatalog;
    public Call<ArrayList<Listas>> requestListas;
    Call<Listas> requestListaAdaptador;
    public Context context = this;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @OnClick(R.id.agregarMejorPrecio)public void agregarProducto(){

        showPopup();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_por_codigo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        buscarProducto(intent.getStringExtra("idProducto"));

    }
    public void buscarProducto(final String codigo) {
        //OBTENER UBICACION

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://maprecios.azurewebsites.net/")
                .build();
        service = retrofit.create(ApiPrecios.class);

        double lati ,lng;
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        if(sharedPreferences.contains("Lat")){
            lati = Double.parseDouble(sharedPreferences.getString("Lat",""));
            lng = Double.parseDouble(sharedPreferences.getString("Longitude",""));
        }else {lati = 0.0; lng = 0.0;}
        //OBTENER UBICACION
        requestCatalog = service.getProducto(codigo, lati, lng);
        requestCatalog.enqueue(new Callback<com.preciosclaros.modelo.Response>() {
            @Override
            public void onResponse(Call<com.preciosclaros.modelo.Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Producto received = response.body().getProducto();
                    mejorProducto = received;
                    Picasso.with(context).load("https://imagenes.preciosclaros.gob.ar/productos/"+codigo+".jpg")
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.no_image_aivalable)
                            .into(imgProducto);
                    precioProducto.setText("$"+response.body().getMejorPrecio());
                    nombreProducto.setText(received.getNombre());
                    ArrayList<Sucursales> sucursales = response.body().getProductos();
                    mejorSucursal = sucursales.get(0);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                            getApplicationContext()
                    ));
                    SucursalesAdapter adapter = new SucursalesAdapter(sucursales,context);
                    // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recyclerView.setAdapter(adapter);
                    Log.i(TAG, "Artículo descargado: ");
                } else {
                    int code = response.code();
                    String c = String.valueOf(code);
                }


            }

            @Override
            public void onFailure(Call<com.preciosclaros.modelo.Response> call, Throwable t) {
                Log.e(TAG, "Error:" + t.getCause());

            }

        });
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        VerProductoPorId.Localizacion Local = new VerProductoPorId.Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

       /* mensaje1.setText("Localizacion agregada");
        mensaje2.setText("");*/
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                   /* mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        VerProductoPorId mainActivity;

        public VerProductoPorId getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(VerProductoPorId mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
            // get editor to edit in file
            editor = sharedPreferences.edit();
            editor.putLong("Latitude", Double.doubleToLongBits(loc.getLatitude()));
            editor.putString("Lat", String.valueOf(loc.getLatitude()));
            editor.putString("Longitude", String.valueOf(loc.getLongitude()));
            editor.commit();
            /*String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            mensaje1.setText(Text);*/
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(getApplicationContext(),"GPS Desactivado",Toast.LENGTH_SHORT).show();
            // mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Toast.makeText(getApplicationContext(),"GPS Activado",Toast.LENGTH_SHORT).show();
            //mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
    public void showPopup(){
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.agregar_producto,
                    (ViewGroup) findViewById(R.id.agregar_prod));

            sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
            int idUser = 0;
            requestListas = service.getListas(sharedPreferences.getInt("id",idUser));
            requestListas.enqueue(new Callback<ArrayList<Listas>>() {
                @Override
                public void onResponse(Call<ArrayList<Listas>> call, retrofit2.Response<ArrayList<Listas>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Listas> listas = response.body();
                        ls = listas;
                  /*  int i =0;
                    for (Listas Nombre :listas) {
                        select[i] = Nombre.getNombre();
                        i++;
                    }*/
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
            String[] valores ={ls.get(0).getNombre(),ls.get(1).getNombre()};
            spinner = (Spinner) layout.findViewById(R.id.select);
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                {
                    Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                    // vacio

                }
            });
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            cantidad = (EditText) layout.findViewById(R.id.Cantidad);
            Close = (Button) layout.findViewById(R.id.btnCerrarProducto);
            Close.setOnClickListener(cancel_button);
            CrearProd = (Button) layout.findViewById(R.id.btnAgregarProducto);
            CrearProd.setOnClickListener(agregar_producto_lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private  View.OnClickListener agregar_producto_lista = new View.OnClickListener() {
        public void onClick(View v) {
            int c = Integer.parseInt(cantidad.getText().toString());
            int p =   mejorSucursal.getPreciosProducto().getPrecioLista().intValue();
            Call<Listas> requestLista = service.AgregarProducto(15,mejorProducto.getId().toString(), c,
                    p, mejorSucursal.getComercioId()+"-"+mejorSucursal.getBanderaId()+"-"+mejorSucursal.getId());
            requestLista.enqueue(new Callback<Listas>() {
                @Override
                public void onResponse(Call<Listas> call, retrofit2.Response<Listas> response) {
                    if (response.isSuccessful()) {
                        Listas received = response.body();
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<Listas> call, Throwable t) {
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
            pw.dismiss();

        }
    };
    public void showPopupAdaptador( Sucursales sucursal){
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = getLayoutInflater();
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.agregar_producto,
                    (ViewGroup) findViewById(R.id.agregar_prod));
            String [] valores = {"lista 1","lista 2","lista 3"};
            spinner = (Spinner) layout.findViewById(R.id.select);
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                {
                    Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                    // vacio

                }
            });
            sucursalElegida = sucursal;
            pw = new PopupWindow(layout, 900,500, true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            cantidad = (EditText) layout.findViewById(R.id.Cantidad);
            Close = (Button) layout.findViewById(R.id.btnCerrarProducto);
            Close.setOnClickListener(cancel_button_adaptador);
            CrearProd = (Button) layout.findViewById(R.id.btnAgregarProducto);

            CrearProd.setOnClickListener(agregar_producto_lista_adaptador);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_adaptador = new View.OnClickListener() {
        public void onClick(View v) {

            pw.dismiss();
        }
    };
    private  View.OnClickListener agregar_producto_lista_adaptador = new View.OnClickListener() {
        public void onClick(View v) {
            int c = Integer.parseInt(cantidad.getText().toString());
            int p =   sucursalElegida.getPreciosProducto().getPrecioLista().intValue();
            requestListaAdaptador = service.AgregarProducto(15,mejorProducto.getId().toString(), c,
                    p,
                    sucursalElegida.getComercioId()+"-"+sucursalElegida.getBanderaId()+"-"+sucursalElegida.getId());
            requestListaAdaptador.enqueue(new Callback<Listas>() {
                @Override
                public void onResponse(Call<Listas> call, retrofit2.Response<Listas> response) {
                    if (response.isSuccessful()) {
                        Listas received = response.body();
                        Log.i(TAG, "Artículo descargado: ");
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                    }


                }

                @Override
                public void onFailure(Call<Listas> call, Throwable t) {
                    Log.e(TAG, "Error:" + t.getCause());

                }

            });
            pw.dismiss();

        }
    };
}
