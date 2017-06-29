package com.preciosclaros;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity  {

   @OnClick({R.id.listas ,R.id.escanear, R.id.buscar}) public void elejirOpcion(ImageButton btn){
        switch (btn.getId()){
            case R.id.listas:
                                Intent intent = new Intent(HomeActivity.this,MisListas.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
            break;
            case R.id.escanear:
                                Intent intent2 = new Intent(HomeActivity.this,BarCode.class);
                                intent2.setFlags(intent2.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent2);
            break;
            case R.id.buscar:
                                Intent intent3 = new Intent(HomeActivity.this,BuscarProductos.class);
                                intent3.setFlags(intent3.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent3);
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);

    }
    @Override
    public void onBackPressed() {

           moveTaskToBack(true);
        }

}

