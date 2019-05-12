package com.example.user.qbike;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Marker;
import android.support.v7.app.AlertDialog;

import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import java.util.Date;

import java.util.ArrayList;

//public class Strona2 extends Fragment implements  OnMapReadyCallback {
public class Strona2 extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";


    private GoogleMap googleMap;

    static MediaPlayer mpZaszybko;
    static MediaPlayer mpMeta;

    static LocationListener listenerLokalizacji;

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();


    /////// poly /////////
    PolylineOptions polylineOptions;
    ArrayList<LatLng> arrayPoints;

    //////////////////////
    private LatLng position;
    private Marker mPosition;

    double SzerStara = 0;
    double DlugStara = 0;

    int wykonaniaDoPolyLine = 0;

    private ObslugaBazy zb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_strona2,container, false);

        final TextView textView1 = (TextView)rootView.findViewById(R.id.textView2View2);

///////////////////////////  // Restore preferences
        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);

        final int ostatniNrPliku = settings.getInt("nrPliku", 1);

        int CacheWaga = settings.getInt("Waga",50);
        int CacheOdswiezanie = settings.getInt("Odswiezanie",3000);
        int CacheDokladnosc = settings.getInt("Dokladnosc",3);
        int CachePrzyblizenie = settings.getInt("Zoom",15);
        int CacheOstatniaTrasa = settings.getInt("OstatniaTrasa",1);

        StatFun.nrpliku = ostatniNrPliku;
        StatFun.ostatniaTrasa = CacheOstatniaTrasa;

        StatFun.masa = CacheWaga;
        StatFun.czasodswiezania = CacheOdswiezanie;
        StatFun.dokladnoscOdczytu = CacheDokladnosc;
        StatFun.przyblizenieMapy = CachePrzyblizenie;
///////////////////////////

        final LocationManager managerLokalizacji = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);

        listenerLokalizacji = new LocationListener() {

            @Override
            public void onLocationChanged(Location nowalokacja) {

                double SzerNowa = nowalokacja.getLatitude();
                double DlugNowa = nowalokacja.getLongitude();
                double WysNowa = nowalokacja.getAltitude();


                ////////////////// rysowanie polyline //////////////////
                position = new LatLng(SzerNowa, DlugNowa);

                if (mPosition != null) {
                    mPosition.remove();
                }

                mPosition = mGoogleMap.addMarker(new MarkerOptions().position(position).title("Your position"));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, StatFun.przyblizenieMapy));

                if(wykonaniaDoPolyLine > 0) {
                    mGoogleMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(SzerStara, DlugStara), new LatLng(SzerNowa, DlugNowa))
                            .width(5)
                            .color(Color.RED));
                }

                SzerStara = SzerNowa;
                DlugStara = DlugNowa;
                wykonaniaDoPolyLine++;
                ///////////////////////////////////////////////////////////////

                StatFun.ustawNowaLokacja(SzerNowa,DlugNowa,WysNowa);

                String text = StatFun.wypiszWspolrzedne();


                //Lokalizacja.setText(text+"\n"+wysokosc);

                if(StatFun.odczytanepunkty>0)
                {
                    // pomiar dystansu
                    StatFun.odcinek = StatFun.poprzednialokacja.distanceTo(nowalokacja);

                    StatFun.dystans += StatFun.odcinek;

                    // wypisanie dystansu

                    StatFun.datapo= new Date();

                    StatFun.obliczCzasRuchu();

                    StatFun.dataprzed = StatFun.datapo;

                    //pomiar prędkości
                    StatFun.obliczPredkosc(nowalokacja.getSpeed());

                    StatFun.obliczKalorie();

                    // czas trasy
                    StatFun.obliczCzasOdStartu();

                    //srednia predkosc
                    StatFun.obliczPredkoscSred();

                    //predkosc max
                    textView1.setText(StatFun.wypiszDystans() + "km");


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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        zb = new ObslugaBazy(this.getContext());
        zb.open();


        Button button = (Button) rootView.findViewById(R.id.buttonZapisz);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder altdial = new AlertDialog.Builder(getContext());
                altdial.setMessage("Chcesz wyjść do menu?").setCancelable(false)
                        .setPositiveButton("Wyjdź i zapisz trasę", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if(StatFun.odczytanepunkty > 0) {

                                    Toast.makeText(getContext(), "Zapisuję trase... ", Toast.LENGTH_SHORT).show();

                                    StatFun.zapiszTraseDoPliku();

                                    zb.DB_DodajTrase("Trasa" + StatFun.nrpliku, StatFun.obecnaGodzina(), StatFun.wypiszCzasOdStartu(), StatFun.wypiszDystans()
                                            , StatFun.wypiszPredkoscSred(), StatFun.wypiszPredMax(), StatFun.wypiszKalorie());

                                    for (int i = 0; i < StatFun.listapozycji.size(); i++) {
                                        String s = (String) StatFun.listapozycji.get(i);

                                        String dl = s.substring(16, 30);
                                        String szer = s.substring(0, 15);
                                        String data = s.substring(32, 42);
                                        String czas = s.substring(43, 51);
                                        String wys = s.substring(s.indexOf("^") + 1, s.indexOf("#"));
                                        String pred = s.substring(s.indexOf("#") + 1, s.indexOf("$"));

                                        //Log.d("dane: ",StatFun.listapozycji.size()+"");
                                        //Log.d("dane: ",dl+" "+szer+" "+wys+" "+czas+" "+data+" "+pred+" "+StatFun.ostatniaTrasa);
                                        zb.DB_DodajPunktTrasy(dl, szer, wys, czas, data, pred, StatFun.nrpliku);
                                    }

                                    Toast.makeText(getContext(), "Zapisano trase " + StatFun.nrpliku, Toast.LENGTH_SHORT).show();

                                    StatFun.listapozycji.clear();
                                    StatFun.ostatniaTrasa++;
                                    StatFun.nrpliku++;

                                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putInt("nrPliku", StatFun.nrpliku);
                                    editor.putInt("OstatniaTrasa", StatFun.ostatniaTrasa);


                                    // Commit the edits!
                                    editor.commit();

                                    getActivity().finish();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Brak punktów GPS", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            }
                        })
                        .setNegativeButton("Wyjdź bez zapisywania", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });

                AlertDialog alert = altdial.create();
                alert.setTitle("Usuwanie trasy");
                alert.show();


            }
        });




        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //googleMap.addMarker(new MarkerOptions().position(new LatLng(52.4068, 16.9223)).title("Poznan").snippet("cos"));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(52.4068, 16.9223)).zoom(12).bearing(0).tilt(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

        /*Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(52.4068, 16.9223), new LatLng(52.5068, 16.8223))
                .width(5)
                .color(Color.RED));*/
    }

    public void onDestroy() {
        if(zb != null)
            zb.close();
        super.onDestroy();
    }



}
