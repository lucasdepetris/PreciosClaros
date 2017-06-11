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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create( new Gson() ))
                .baseUrl("http://localhost:59395/")
                .build();
        ApiPrecios service = retrofit.create(ApiPrecios.class);
        Call<Producto> requestCatalog = service.getProducto("7790040100336",-34.666227,-58.589724);

        requestCatalog.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()) {
                    Producto received = response.body();
                    Log.i(TAG, "Artículo descargado: " +
                            received.getNombre());
                    textViewName.setText(received.getNombre());
                }}

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e(TAG,"Error:"+t.getMessage());
            }
        });
        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        buttonScan.setOnClickListener(this);
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
}