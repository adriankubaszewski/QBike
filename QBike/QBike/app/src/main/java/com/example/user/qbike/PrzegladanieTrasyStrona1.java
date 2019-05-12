package com.example.user.qbike;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class PrzegladanieTrasyStrona1 extends Fragment {

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";

    private ObslugaBazy zb;


    EditText nowaNazwa;
    String odczytanaNowaNazwa="";

    String ZmianaNazwaTrasy = "";
    String ZmianaDataUtworzenia = "";
    String ZmianaDystans = "";
    String ZmianaPredkoscSrednia = "";
    String ZmianaPredkoscMax = "";
    String ZmianaCzasTrwania = "";
    String ZmianaKCAL = "";

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_przegladanietrasy_strona1,container, false);

        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);

        final int CacheOstatniaTrasa = settings.getInt("OstatniaTrasa",1);


        //////////////////////////
        /////
        /*
            - minimalna, max wysokosc
            - odczytane punkty

         */
        /////
        //////////////////////////

        final TextView NazwaTrasy = (TextView) rootView.findViewById(R.id.textViewNazwaTrasy);
        final TextView DataUtworzenia = (TextView) rootView.findViewById(R.id.textViewDataUtworzenia);
        final TextView Dystans = (TextView) rootView.findViewById(R.id.textViewDystans);
        final TextView PredkoscSrednia = (TextView) rootView.findViewById(R.id.textViewSrredniaPred);
        final TextView PredkoscMax = (TextView) rootView.findViewById(R.id.textViewPredkoscMax);
        final TextView CzasTrwania = (TextView) rootView.findViewById(R.id.textViewCzasTrwania);
        final TextView KCAL = (TextView) rootView.findViewById(R.id.textViewSpaloneKCAL);


        zb = new ObslugaBazy(this.getContext());
        zb.open();


        TabelaTrasyOperacje k = zb.DB_WczytajTrase(CacheOstatniaTrasa);
        Log.d("dane: ",k.getIdTrasy()+" "+k.getNazwaTrasy()+" "+k.getDataUtworzenia()+" "+k.getCzasTrwania()+" "
                +k.getPredkoscSrednia());

        ZmianaNazwaTrasy = k.getNazwaTrasy();
        ZmianaDataUtworzenia = k.getDataUtworzenia();
        ZmianaDystans = k.getDystans();
        ZmianaPredkoscSrednia = k.getPredkoscSrednia();
        ZmianaPredkoscMax = k.getPredkoscMax();
        ZmianaCzasTrwania = k.getCzasTrwania();
        ZmianaKCAL = k.getSpaloneKcal();

        NazwaTrasy.setText(k.getNazwaTrasy());
        DataUtworzenia.setText(k.getDataUtworzenia());
        Dystans.setText(k.getDystans()+" km");
        PredkoscSrednia.setText(k.getPredkoscSrednia()+" km/h");
        PredkoscMax.setText(k.getPredkoscMax()+" km/h");
        CzasTrwania.setText(k.getCzasTrwania());
        KCAL.setText(k.getSpaloneKcal()+" kcal");

        zb.close();

        zb = new ObslugaBazy(this.getContext());
        zb.open();


        //// USUWANIE TRASY
        Button buttonUsun = (Button) rootView.findViewById(R.id.buttonUsunTrase);
        buttonUsun.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder altdial = new AlertDialog.Builder(getContext());
                altdial.setMessage("Chcesz usunąć trasę z bazy danych?").setCancelable(false)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                zb.DB_UsunTrase(CacheOstatniaTrasa);
                                zb.DB_UsunPunkty(CacheOstatniaTrasa);

                                Toast.makeText(getContext(), "Trase usunięto", Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(getContext(),PrzejechanetrasyActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                //getActivity().finish();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Usuwanie trasy");
                alert.show();
            }
        });


        //// EDYCJA NAZWY
        Button buttonEdytuj = (Button) rootView.findViewById(R.id.buttonEdycjaNazwy);
        buttonEdytuj.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder nowaNazwaDialog = new AlertDialog.Builder(getContext());
                nowaNazwaDialog.setMessage("Wpisz nową nazwę trasy?");
                nowaNazwaDialog.setTitle("Nowa nazwa trasy");

                nowaNazwa = new EditText(getContext());
                nowaNazwaDialog.setView(nowaNazwa);

                nowaNazwaDialog.setPositiveButton("Zmień", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        odczytanaNowaNazwa = nowaNazwa.getText().toString();

                        zb.DB_EdytujTrase(CacheOstatniaTrasa,odczytanaNowaNazwa,ZmianaDataUtworzenia,ZmianaCzasTrwania,ZmianaDystans,ZmianaPredkoscSrednia,ZmianaPredkoscMax,ZmianaKCAL);

                        Toast.makeText(getContext(), "Zmieniono nazwę trasy na: "+odczytanaNowaNazwa, Toast.LENGTH_SHORT).show();

                    }
                });

                nowaNazwaDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog ad = nowaNazwaDialog.create();
                ad.show();

            }
        });



        return rootView;
    }

    public void onDestroy() {
        if(zb != null)
            zb.close();
        super.onDestroy();
    }
}
