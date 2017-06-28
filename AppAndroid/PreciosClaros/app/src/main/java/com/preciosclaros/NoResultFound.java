package com.preciosclaros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lucas on 28/6/2017.
 */

public class NoResultFound extends AppCompatActivity {
    @OnClick(R.id.backHome)public void volverAlHome(){
        Intent intent = new Intent(NoResultFound.this,HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.buscarNuevamente)public void buscarDeNuevo(){
        Intent intent = new Intent(NoResultFound.this,BarCode.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_result_scan);
        ButterKnife.bind(this);


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NoResultFound.this,HomeActivity.class);
        startActivity(intent);
    }
}
