package com.example.user.qbike;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class Strona1 extends Fragment {

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();
    static LocationListener listenerLokalizacji;

    static MediaPlayer mpZaszybko;
    static MediaPlayer mpMeta;

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";

    boolean komunikatMeta = false;
    String czass = "- -";


    public void Zapisz()
    {
        StatFun.zapiszTraseDoPliku();
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_strona1,container, false);


        mpZaszybko = MediaPlayer.create(getActivity(),R.raw.zwolnij);
        mpMeta = MediaPlayer.create(getActivity(),R.raw.meta);

        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);

        int CacheWaga = settings.getInt("Waga",50);
        int CacheOdswiezanie = settings.getInt("Odswiezanie",3000);
        int CacheDokladnosc = settings.getInt("Dokladnosc",3);

        final boolean CacheTrening = settings.getBoolean("Trening",false);
        final int CacheTreningPredkosc = settings.getInt("TreningPred",20);
        final int CacheTreningOdleglosc = settings.getInt("TreningOdleglosc",1000);

        StatFun.masa = CacheWaga;
        StatFun.czasodswiezania = CacheOdswiezanie;
        StatFun.dokladnoscOdczytu = CacheDokladnosc;


        final TextView Predkosc = (TextView) rootView.findViewById(R.id.textViewPred);
        final TextView Odleglosc = (TextView) rootView.findViewById(R.id.textViewDystans);
        final TextView CzasTrasy = (TextView) rootView.findViewById(R.id.textViewCzastrasy);
        final TextView PredkoscMax = (TextView) rootView.findViewById(R.id.textViewPredMax);
        final TextView CzasOdcinka = (TextView) rootView.findViewById(R.id.textViewCzasOdcinka);
        final TextView PredkoscSrednia = (TextView) rootView.findViewById(R.id.textViewSredniapred);
        final TextView Kalorie = (TextView) rootView.findViewById(R.id.kalorie);
        final TextView Wysokosc = (TextView) rootView.findViewById(R.id.textViewWysokosc);


        final LocationManager managerLokalizacji = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);

        listenerLokalizacji = new LocationListener() {

            @Override
            public void onLocationChanged(Location nowalokacja) {

                StatFun.ustawNowaLokacja(nowalokacja.getLatitude(),nowalokacja.getLongitude(),nowalokacja.getAltitude());

                //String text = StatFun.wypiszWspolrzedne();

                //Lokalizacja.setText(text+"\n"+wysokosc);

                if(StatFun.odczytanepunkty>0)
                {
                    // pomiar dystansu
                    StatFun.odcinek = StatFun.poprzednialokacja.distanceTo(nowalokacja);

                    StatFun.dystans += StatFun.odcinek;




                    Wysokosc.setText(StatFun.wypiszWysokosc()+"m");

                    //TekstView2.setText("tekscik");

                    StatFun.datapo= new Date();


                    StatFun.obliczCzasRuchu();

                    //long czasruchu = (StatFun.datapo.getTime()-StatFun.dataprzed.getTime())/1000;
                    StatFun.dataprzed = StatFun.datapo;


                    //pomiar prędkości
                    StatFun.obliczPredkosc(nowalokacja.getSpeed());

                    //float pred = nowalokacja.getSpeed();
                    //float pred = nowalokacja.getSpeed();
                    //float predoblicz = pred * 3.6f;

                    Predkosc.setText(StatFun.wypiszPredkosc() + "km/h");
                    //Predkosc.setText(predoblicz + "km/h");


                    if(CacheTrening == true)
                    {
                        if((CacheTreningPredkosc < StatFun.predkosc + 3)&&(CacheTreningPredkosc > StatFun.predkosc - 3))
                        {
                            //mpZaszybko.start();
                            Predkosc.setTextColor(Color.GREEN);
                            //Predkosc.setTextColor(Color.RED);
                        }
                        else
                        {
                            Predkosc.setTextColor(Color.RED);
                        }

                        Log.d("dane: 1",StatFun.dystans+" "+CacheTreningOdleglosc);
                        if(StatFun.dystans > CacheTreningOdleglosc)
                        {
                            if(komunikatMeta==false)
                            {
                                Odleglosc.setTextColor(Color.GREEN);
                                mpMeta.start();
                                komunikatMeta = true;
                                Log.d("dane: 1","wchodze dystans");
                            }
                            else
                            {
                                Odleglosc.setTextColor(Color.GREEN);
                            }
                        }
                    }



                    // wypisanie dystansu
                    Odleglosc.setText(StatFun.wypiszDystans() + "km");


                    StatFun.obliczKalorie();
                    Kalorie.setText(StatFun.wypiszKalorie()+" ");


                    // czas trasy
                    StatFun.obliczCzasOdStartu();

                    CzasTrasy.setText(StatFun.wypiszCzasOdStartu());

                    //srednia predkosc
                    StatFun.obliczPredkoscSred();
                    PredkoscSrednia.setText(StatFun.wypiszPredkoscSred() + "km/h");

                    //predkosc max
                    PredkoscMax.setText(StatFun.wypiszPredMax() + "km/h");

                    // czas odcinka
                    StatFun.odcinekKilometr += StatFun.odcinek;
                    StatFun.czasKilometra += StatFun.czasruchu;


                    if(StatFun.odcinekKilometr/1000>1)
                    {
                        czass = StatFun.wypiszCzasOdcinka();

                    }
                    CzasOdcinka.setText(czass);

                }
                if(StatFun.odczytanepunkty==0) {
                    StatFun.dataprzed = new Date();
                    StatFun.czasstartu = StatFun.dataprzed;
                }
                StatFun.poprzednialokacja.setLatitude(nowalokacja.getLatitude());
                StatFun.poprzednialokacja.setLongitude(nowalokacja.getLongitude());

                StatFun.odczytanepunkty++;

                //StatFun.listapozycji.add(text);
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
}
