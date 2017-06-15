package com.preciosclaros;

/**
 * Created by lucas on 4/6/2017.
 */
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarCode extends AppCompatActivity implements View.OnClickListener {
    public final String TAG = "" ;
    //View Objects
    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    //qr code scanner object
    private IntentIntegrator qrScan;
    public  String id ;
    ApiPrecios service;
    public Call<com.preciosclaros.Response> requestCatalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create( new Gson() ))
                .baseUrl("https://d735s5r2zljbo.cloudfront.net/prod/")
                .build();
         service = retrofit.create(ApiPrecios.class);
        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        buttonScan.setOnClickListener(this);
       /* id ="7790040100336";
        if(id != "") {


            requestCatalog.enqueue(new Callback<com.preciosclaros.Response>() {
                @Override
                public void onResponse(Call<com.preciosclaros.Response> call, Response<com.preciosclaros.Response> response) {
                    if (response.isSuccessful()) {
                        Producto received = response.body().getProducto();
                        ArrayList<Sucursale> sucursales = response.body().getProductos();
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
                textViewName.setText(result.getContents());
                textViewAddress.setText(result.getFormatName());
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
    public void buscarProducto(String codigo){
        requestCatalog = service.getProducto(codigo, -34.666227, -58.589724);
        requestCatalog.enqueue(new Callback<com.preciosclaros.Response>() {
            @Override
            public void onResponse(Call<com.preciosclaros.Response> call, Response<com.preciosclaros.Response> response) {
                if (response.isSuccessful()) {
                    Producto received = response.body().getProducto();
                    ArrayList<Sucursale> sucursales = response.body().getProductos();
                    Log.i(TAG, "Artículo descargado: ");
                    textViewName.setText(received.getNombre());
                    textViewAddress.setText(received.getMarca());
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
}