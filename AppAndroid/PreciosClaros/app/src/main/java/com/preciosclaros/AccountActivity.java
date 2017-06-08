package com.preciosclaros;

/**
 * Created by lucas on 4/6/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;


public class AccountActivity extends AppCompatActivity{
    private static final String PREFER_NAME = "Reg";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains("Name"))
        {
            String uName = sharedPreferences.getString("Name", "");
            String Email = sharedPreferences.getString("Email", "");
            String FamilyName = sharedPreferences.getString("FamilyName", "");
            TextView usuario = (TextView) findViewById(R.id.user);
            TextView email = (TextView) findViewById(R.id.email);
            TextView family = (TextView) findViewById(R.id.familyname);
            usuario.setText(uName);
            email.setText(Email);
            family.setText(FamilyName);

        }else{TextView usuario = (TextView) findViewById(R.id.user);usuario.setText("hola NOOOO tengo user");}

    }
}
