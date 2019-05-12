package com.example.user.qbike;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;


public class Strona3 extends Fragment {

    int liczba = 0;

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();
    static LocationListener listenerLokalizacji;

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";

    String czas1KMtxt="";
    String czas5KMtxt="";
    String czas10KMtxt="";
    String czas20KMtxt="";
    String czas25KMtxt="";
    String czas50KMtxt="";
    String czas100KMtxt="";

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_strona3,container, false);

        //final TextView tekst = (TextView) rootView.findViewById(R.id.textView2strona3);

        /*Thread t=new Thread()
        {
            @Override
            public void run()
            {
                while(!isInterrupted())
                {
                    try
                    {
                        Thread.sleep(3000);
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                liczba++;
                                tekst.setText(liczba+"");
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();*/

        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);

        int CacheOdswiezanie = settings.getInt("Odswiezanie",3000);
        int CacheDokladnosc = settings.getInt("Dokladnosc",3);

        StatFun.czasodswiezania = CacheOdswiezanie;
        StatFun.dokladnoscOdczytu = CacheDokladnosc;


        final TextView PredkoscSr1KM = (TextView) rootView.findViewById(R.id.textView1KM);
        final TextView PredkoscSr5KM = (TextView) rootView.findViewById(R.id.textView5KM);
        final TextView PredkoscSr10KM = (TextView) rootView.findViewById(R.id.textView10KM);
        final TextView PredkoscSr20KM = (TextView) rootView.findViewById(R.id.textView20KM);
        final TextView PredkoscSr25KM = (TextView) rootView.findViewById(R.id.textView25KM);
        final TextView PredkoscSr50KM = (TextView) rootView.findViewById(R.id.textView50KM);
        final TextView PredkoscSr100KM = (TextView) rootView.findViewById(R.id.textView100KM);


        final LocationManager managerLokalizacji = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);

        listenerLokalizacji = new LocationListener() {

            @Override
            public void onLocationChanged(Location nowalokacja) {

                StatFun.ustawNowaLokacja(nowalokacja.getLatitude(),nowalokacja.getLongitude(),nowalokacja.getAltitude());

                if(StatFun.odczytanepunkty>0)
                {
                    // pomiar dystansu
                    StatFun.odcinek = StatFun.poprzednialokacja.distanceTo(nowalokacja);

                    StatFun.dystans += StatFun.odcinek;

                    StatFun.datapo= new Date();

                    StatFun.obliczCzasRuchu();

                    StatFun.dataprzed = StatFun.datapo;

                    //pomiar prędkości
                    StatFun.obliczPredkosc(nowalokacja.getSpeed());

                    StatFun.obliczCzasOdStartu();

                    //srednia predkosc
                    StatFun.obliczPredkoscSred();
                    //PredkoscSrZwykla.setText(StatFun.wypiszPredkoscSred() + "km/h");

                    //Log.d("dane: dystans ",StatFun.dystans+"");
                    //Log.d("dane: predsred ",StatFun.sredniapredkosc+"");

                    if(StatFun.dystans/1000<1)
                    {
                        czas1KMtxt = ObliczCzas(1,StatFun.sredniapredkosc);
                        PredkoscSr1KM.setText(czas1KMtxt);
                    }
                    else
                    {
                        PredkoscSr1KM.setText(czas1KMtxt);
                    }
                    if(StatFun.dystans/1000<5)
                    {
                        czas5KMtxt = ObliczCzas(5,StatFun.sredniapredkosc);
                        PredkoscSr5KM.setText(czas5KMtxt);
                    }
                    else
                    {
                        PredkoscSr5KM.setText(czas5KMtxt);
                    }
                    if(StatFun.dystans/1000<10)
                    {
                        PredkoscSr10KM.setText(ObliczCzas(10,StatFun.sredniapredkosc));
                    }
                    else
                    {
                        PredkoscSr10KM.setText(czas10KMtxt);
                    }
                    if(StatFun.dystans/1000<20)
                    {
                        PredkoscSr20KM.setText(ObliczCzas(20,StatFun.sredniapredkosc));
                    }
                    else
                    {
                        PredkoscSr20KM.setText(czas20KMtxt);
                    }
                    if(StatFun.dystans/1000<25)
                    {
                        PredkoscSr25KM.setText(ObliczCzas(25,StatFun.sredniapredkosc));
                    }
                    else
                    {

                    }
                    if(StatFun.dystans/1000<50)
                    {
                        PredkoscSr50KM.setText(ObliczCzas(50,StatFun.sredniapredkosc));
                    }
                    if(StatFun.dystans/1000<100)
                    {
                        PredkoscSr100KM.setText(ObliczCzas(100,StatFun.sredniapredkosc));
                    }


                }
                if(StatFun.odczytanepunkty==0) {
                    StatFun.dataprzed = new Date();
                    StatFun.czasstartu = StatFun.dataprzed;
                }
                StatFun.poprzednialokacja.setLatitude(nowalokacja.getLatitude());
                StatFun.poprzednialokacja.setLongitude(nowalokacja.getLongitude());

                StatFun.odczytanepunkty++;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //return;
        }
        managerLokalizacji.requestLocationUpdates(LocationManager.GPS_PROVIDER, StatFun.czasodswiezania, StatFun.dokladnoscOdczytu, listenerLokalizacji);



        return rootView;
    }

    public String ObliczCzas(int cel, double predkosc)
    {
        String ObliczonyCzas = "";

        double minuty = cel * 60 / predkosc;

        int godziny = (int)minuty/60;
        int minutyodliczone = (int)minuty%60;

        ObliczonyCzas = godziny+":";

        if(minutyodliczone<10)
        {
            ObliczonyCzas+="0"+minutyodliczone;
        }
        else
        {
            ObliczonyCzas+=""+minutyodliczone;
        }

        return ObliczonyCzas;
    }
}
