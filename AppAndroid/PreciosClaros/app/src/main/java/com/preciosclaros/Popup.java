package com.preciosclaros;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

/**
 * Created by lucas on 8/6/2017.
 */

public class Popup extends AppCompatActivity {
    Button btn_Abrir_Popup;
    Button btn_Cerrar;
    LayoutInflater layoutInflater;
    View popupView;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        btn_Abrir_Popup = (Button)findViewById(R.id.button1);
        btn_Abrir_Popup.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                layoutInflater =(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.popup, null);

                popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
                btn_Cerrar = (Button)popupView.findViewById(R.id.close_popup);
                btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(btn_Abrir_Popup, 50, 0);

            }});
}
}
