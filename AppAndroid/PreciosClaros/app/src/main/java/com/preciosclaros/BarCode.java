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
import com.google.zxing.integration.android.IntentResult;
import com.preciosclaros.adaptadores.SucursalesAdapter;
import com.preciosclaros.modelo.Listas;
import com.preciosclaros.modelo.Producto;
import com.preciosclaros.modelo.Sucursales;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BarCode extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    private IntentIntegrator qrScan;
    public String id;
    public Context context = this;
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
                BarCode.this.finish();
                Intent intent = new Intent(BarCode.this,NoResultFound.class);
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                //if qr contains data
                BarCode.this.finish();
                Intent intent = new Intent(BarCode.this, VerProductoPorId.class);
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("idProducto",result.getContents());
                startActivity(intent);
                //buscarProducto(result.getContents());
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BarCode.this,HomeActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}