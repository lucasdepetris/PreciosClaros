package com.preciosclaros;

/**
 * Created by lucas on 4/6/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
                .baseUrl("https://d735s5r2zljbo.cloudfront.net/prod/")
                .build();
        service = retrofit.create(ApiPrecios.class);
        //View objects
       /* buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);*/
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        qrScan.initiateScan();
       // buttonScan.setOnClickListener(this);
       /* id ="7790040100336";
        if(id != "") {


            requestCatalog.enqueue(new Callback<com.preciosclaros.Response>() {
                @Override
                public void onResponse(Call<com.preciosclaros.Response> call, Response<com.preciosclaros.Response> response) {
                    if (response.isSuccessful()) {
                        Producto received = response.body().getProducto();
                        ArrayList<Sucursales> sucursales = response.body().getProductos();
                        Log.i(TAG, "Artículo descargado: ");
                        textViewName.setText(received.getNombre());
                    } else {
                        int code = response.code();
                        String c = String.valueOf(code);
                        textViewName.setText(c);
                    }


                }

                @Override
                public void onFailure(Call<com.preciosclaros.Response> call, Throwable t) {
                    Log.e(TAG, "Error:" + t.getCause());
                    textViewName.setText(t.getMessage());
                    textViewAddress.setText("failure");

                }

            });
        }
*/
        /*Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create( new Gson() ))
                .baseUrl("https://api.mercadolibre.com/")
                .build();
        ApiPrecios service = retrofit.create(ApiPrecios.class);
        Call<Article> requestCatalog = service.getArticle("MLA644287324");

        requestCatalog.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()) {
                    Article received = response.body();
                    Log.i(TAG, "Artículo descargado: " +
                            received.getId());

                    textViewName.setText(received.getId());
                }}

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG,"Error:"+t.getMessage());
            }
        });
        */
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
       /* LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitud = location.getLongitude();*/
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
}