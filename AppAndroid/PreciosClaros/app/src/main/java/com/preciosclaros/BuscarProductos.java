package com.preciosclaros;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lucas on 22/6/2017.
 */

public class BuscarProductos extends AppCompatActivity {

    @BindView(R.id.ProductoBuscar)
    EditText buscar;
    @OnClick(R.id.BuscarProductosBtn)public void buscar(){
        Toast.makeText(this,buscar.getText().toString(),Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_productos);
        ButterKnife.bind(this);


    }
}
