package com.preciosclaros;

/**
 * Created by lucas on 5/6/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu1 extends Fragment{
    private static final String PREFER_NAME = "Reg";
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        sharedPreferences = getActivity().getSharedPreferences(PREFER_NAME,Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.activity_account, container, false);
        if (sharedPreferences.contains("Name"))
        {
            String uName = sharedPreferences.getString("Name", "");
            String Email = sharedPreferences.getString("Email", "");
            String FamilyName = sharedPreferences.getString("FamilyName", "");
            TextView usuario = (TextView) v.findViewById(R.id.user);
            TextView email = (TextView) v.findViewById(R.id.email);
            TextView family = (TextView) v.findViewById(R.id.familyname);
            usuario.setText(uName);
            email.setText(Email);
            family.setText(FamilyName);

        }else{TextView usuario = (TextView) v.findViewById(R.id.user);usuario.setText("hola NOOOO tengo user");}
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }
}