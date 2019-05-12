package com.example.user.qbike;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;




public class InstrukcjaObslugiActivity extends AppCompatActivity{

    private TextView Lokalizacja;
    private TextView Odleglosc;
    private TextView Predkosc;
    private SeekBar SuwakCzestotliwosci;


    private ObslugaBazy zb;
    //public ListView listaTras;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrukcjaobslugi);


    }

    protected void onDestroy() {
        if(zb != null)
            zb.close();
        super.onDestroy();
    }
}
