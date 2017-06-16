package com.preciosclaros;

/**
 * Created by lucas on 4/6/2017.
 */

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class BarCode extends AppCompatActivity implements View.OnClickListener {
    public final String TAG = "";
    //View Objects
    private Button buttonScan;
    private TextView  nombreProducto, precioProducto;
    private ImageView imgProducto;
    private ListView lista;
    private IntentIntegrator qrScan;
    public String id;
    ApiPrecios service;
    public Call<com.preciosclaros.Response> requestCatalog;
    public Context context = this;
    public Double latitud ,longitud;
    private LocationManager locationManager;
    private LocationListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_por_codigo);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl("http://maprecios.azurewebsites.net/")
                .build();
        service = retrofit.create(ApiPrecios.class);
        //View objects

        //intializing scan object
        qrScan = new IntentIntegrator(this);
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
                //in this case you can display whatever data is available on the qrcode
                //to a toast
                buscarProducto(result.getContents());
                //textViewName.setText(result.getContents());
                //textViewAddress.setText(result.getFormatName());
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                String coordenadas = "Mis coordenadas son: " + "Latitud = " + location.getLatitude() +"Longitud = "+ location.getLongitude()+"";
                Toast.makeText(getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        configure_button();
        //OBTENER UBICACION

        double d = -34.666227;
        requestCatalog = service.getProducto(codigo, d, -58.589724);
        requestCatalog.enqueue(new Callback<com.preciosclaros.Response>() {
            @Override
            public void onResponse(Call<com.preciosclaros.Response> call, Response<com.preciosclaros.Response> response) {
                if (response.isSuccessful()) {
                    Producto received = response.body().getProducto();
                    nombreProducto = (TextView) findViewById(R.id.MejorNombre);
                    imgProducto = (ImageView) findViewById(R.id.MejorImgProducto) ;
                    Picasso.with(context).load("https://imagenes.preciosclaros.gob.ar/productos/"+codigo+".jpg").into(imgProducto);
                    //precioProducto = (TextView) findViewById(R.id.MejorPrecio);
                    nombreProducto.setText(received.getNombre());
                    ArrayList<Sucursales> sucursales = response.body().getProductos();
                    SucursalesAdapter adapter = new SucursalesAdapter(context, sucursales);
                    lista =(ListView) findViewById(R.id.listaProductoSucursales);
                    lista.setAdapter(adapter);
                    String s1 = String.valueOf(latitud);
                    Log.i(TAG, "Artículo descargado: "+s1);



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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

    }
}