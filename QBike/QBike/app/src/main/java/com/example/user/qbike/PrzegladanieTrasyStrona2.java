package com.example.user.qbike;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.ArrayList;
import java.util.Date;

//import android.support.v4.app.FragmentActivity;

//public class Strona2 extends Fragment implements  OnMapReadyCallback {
public class PrzegladanieTrasyStrona2 extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";


    private GoogleMap googleMap;

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();


    /////// poly /////////
    PolylineOptions polylineOptions;
    ArrayList<LatLng> arrayPoints;

    //////////////////////
    private LatLng position;
    private Marker mPosition;
    private Marker mPosition2;

    double SzerStara = 0;
    double DlugStara = 0;

    int wykonaniaDoPolyLine = 0;

    private ObslugaBazy zb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_przegladanietrasy_strona2,container, false);


        /*PolylineOptions polylineOptions = new PolylineOptions();

        polylineOptions.addAll(coordList);
        polylineOptions.width(5).color(Color.RED);

        mGoogleMap.addPolyline(polylineOptions);*/


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(52.4068, 16.9223)).zoom(12).bearing(0).tilt(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

        //////////////////////////////////////////

        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);

        int CachePrzyblizenie = settings.getInt("Zoom",15);
        int CacheOstatniaTrasa = settings.getInt("OstatniaTrasa",1);

        StatFun.ostatniaTrasa = CacheOstatniaTrasa;

        StatFun.przyblizenieMapy = CachePrzyblizenie;

        zb = new ObslugaBazy(this.getContext());
        zb.open();

        double dlug = 0.0;
        double szer = 0.0;

        String dlugosc = "";
        String szerokosc = "";
        String pom = "";

        ArrayList<LatLng> coordList = new ArrayList<LatLng>();

        for(TabelaPunktyTrasyOperacje k:zb.DB_WczytajPunktyTrasy(CacheOstatniaTrasa)){

            Log.d("dane:",k.getIDPunktTrasy()+" "+k.getDlugoscGeo()+" "+k.getSzerokoscGeo()+" "+k.getWysokosc()+" "
                    +k.getPredkoscPunktu()+" "+k.getIdTrasy());


            pom=k.getDlugoscGeo();
            dlugosc = pom.replace(',', '.');
            dlug = Double.parseDouble(dlugosc);

            pom=k.getSzerokoscGeo();
            szerokosc = pom.replace(',', '.');
            szer = Double.parseDouble(szerokosc);

            coordList.add(new LatLng(szer, dlug));

            ////////////////// rysowanie polyline //////////////////
            position = new LatLng(szer, dlug);

            if (mPosition != null) {
                mPosition.remove();
            }

            if(wykonaniaDoPolyLine == 0)
            {
                mPosition2 = googleMap.addMarker(new MarkerOptions().position(position).title("Start"));
            }

            mPosition = googleMap.addMarker(new MarkerOptions().position(position).title("Meta"));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, StatFun.przyblizenieMapy));

            if(wykonaniaDoPolyLine > 0) {
                googleMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(SzerStara, DlugStara), new LatLng(szer, dlug))
                        .width(5)
                        .color(Color.RED));
            }

            SzerStara = szer;
            DlugStara = dlug;
            wykonaniaDoPolyLine++;

            StatFun.odczytanepunkty++;
        }

        /*Polyline line =googleMap.addPolyline(new PolylineOptions()
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
