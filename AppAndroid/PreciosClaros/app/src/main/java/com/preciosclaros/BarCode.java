package com.preciosclaros;

/**
 * Created by lucas on 4/6/2017.
 */


import android.Manifest;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BarCode extends AppCompatActivity implements View.OnClickListener {
    public final String TAG = "";
    //View Objects
    @BindView(R.id.MejorNombre) TextView nombreProducto;
    @BindView(R.id.MejorPrecio) TextView precioProducto;
    @BindView(R.id.MejorImgProducto) ImageView imgProducto;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private IntentIntegrator qrScan;
    public String id;
    ApiPrecios service;
    public Call<com.preciosclaros.Response> requestCatalog;
    public Context context = this;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_por_codigo);
        ButterKnife.bind(this);


        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setBeepEnabled(false);
        //attaching onclick listener
        qrScan.initiateScan();

    }
    //Getting the scan results

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                buscarProducto(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

    public void buscarProducto(final String codigo) {
        //OBTENER UBICACION

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
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
        requestCatalog.enqueue(new Callback<com.preciosclaros.Response>() {
            @Override
            public void onResponse(Call<com.preciosclaros.Response> call, Response<com.preciosclaros.Response> response) {
                if (response.isSuccessful()) {
                    Producto received = response.body().getProducto();
                    Picasso.with(context).load("https://imagenes.preciosclaros.gob.ar/productos/"+codigo+".jpg").into(imgProducto);
                    precioProducto.setText(response.body().getMejorPrecio());
                    nombreProducto.setText(received.getNombre());
                    ArrayList<Sucursales> sucursales = response.body().getProductos();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    SucursalesAdapter adapter = new SucursalesAdapter(sucursales);
                   // lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    recyclerView.setAdapter(adapter);
                    Log.i(TAG, "Artículo descargado: ");
                } else {
                    int code = response.code();
                    String c = String.valueOf(code);
                }


            }

            @Override
            public void onFailure(Call<com.preciosclaros.Response> call, Throwable t) {
                Log.e(TAG, "Error:" + t.getCause());

            }

        });
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
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
        BarCode mainActivity;

        public BarCode getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(BarCode mainActivity) {
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
}