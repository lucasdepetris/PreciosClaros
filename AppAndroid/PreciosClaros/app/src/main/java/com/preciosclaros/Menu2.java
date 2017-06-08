package com.preciosclaros;

/**
 * Created by lucas on 5/6/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu2 extends Fragment implements View.OnClickListener {
    //qr code scanner object
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View myinflatedview = inflater.inflate(R.layout.activity_barcode, container, false);
        // Set the Text to try this out
        TextView t = (TextView) myinflatedview.findViewById(R.id.textViewName);
        Button btn = (Button) myinflatedview.findViewById(R.id.buttonScan);
        btn.setOnClickListener(this);
        t.setText("Text to Display");
        return myinflatedview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 2");
    }

    @Override
    public void onClick(View v) {
        TextView tx = (TextView) getView().findViewById(R.id.textViewAddress);
        tx.setText("hola");
        IntentIntegrator it = new IntentIntegrator(getActivity());

        it.initiateScan();

    }

}
