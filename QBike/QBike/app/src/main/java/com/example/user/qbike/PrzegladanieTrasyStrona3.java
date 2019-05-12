package com.example.user.qbike;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;


public class PrzegladanieTrasyStrona3 extends Fragment {

    private LineChart lineChart;
    private LineChart lineChartPredkosc;

    public static final String PREFS_NAME = "QBikePrefsFile";

    final StatystkiFunkcje StatFun = new StatystkiFunkcje();

    private ObslugaBazy zb;

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_przegladanietrasy_strona3,container, false);

        lineChart = (LineChart) rootView.findViewById(R.id.WykresWysokosci);
        lineChartPredkosc = (LineChart) rootView.findViewById(R.id.WykresPredkosci);

        SharedPreferences settings =  getActivity().getSharedPreferences(PREFS_NAME, 0);
        int CacheOstatniaTrasa = settings.getInt("OstatniaTrasa",1);

        StatFun.ostatniaTrasa = CacheOstatniaTrasa;

        zb = new ObslugaBazy(this.getContext());
        zb.open();

        float wys = 0;
        float pred = 0;

        String wysokosc = "";
        String predkosc = "";
        String pom = "";

        ArrayList<Float> osY = new ArrayList<>();
        ArrayList<String> osX = new ArrayList<>();

        ArrayList<Float> osYPredkosc = new ArrayList<>();
        ArrayList<String> osXPredkosc = new ArrayList<>();



        for(TabelaPunktyTrasyOperacje k:zb.DB_WczytajPunktyTrasy(CacheOstatniaTrasa))
        {
            pom=k.getWysokosc();
            wysokosc = pom.replace(',', '.');
            wys = Float.parseFloat(wysokosc);
            osY.add(wys);
            osX.add(" ");

            pom = k.getPredkoscPunktu();
            predkosc = pom.replace(',', '.');
            pred = Float.parseFloat(predkosc);
            osYPredkosc.add(pred);
            osXPredkosc.add(" ");

            StatFun.odczytanepunkty++;
        }


        ///////////// RYSOWANIE WYKRESU WYSOKOSCI

        lineChart.setDescription("Wysokosci");

        ArrayList<Entry> daneY = new ArrayList<>();

        for(int i = 0; i<osY.size(); i++)
        {
            daneY.add(new Entry(osY.get(i),i));
        }

        ArrayList<String> daneX = new ArrayList<>();
        for(int i = 0; i<osX.size(); i++)
        {
            daneX.add(osX.get(i));
        }

        LineDataSet lineDataSet = new LineDataSet(daneY, "Wysokosci");
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);

        LineData lineData = new LineData(daneX,lineDataSet);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);

        //////////////   RYSOWANIE WYKRESU PREDKOSCI

        lineChartPredkosc.setDescription("Predkosci");

        ArrayList<Entry> daneYPred = new ArrayList<>();

        for(int i = 0; i<osYPredkosc.size(); i++)
        {
            daneYPred.add(new Entry(osYPredkosc.get(i),i));
        }

        ArrayList<String> daneXPred = new ArrayList<>();
        for(int i = 0; i<osXPredkosc.size(); i++)
        {
            daneXPred.add(osXPredkosc.get(i));
        }

        LineDataSet lineDataSet2 = new LineDataSet(daneYPred, "Predkosci");
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setFillColor(Color.BLUE);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setDrawCubic(true);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setDrawCircles(false);

        LineData lineData2 = new LineData(daneXPred,lineDataSet2);
        lineData2.setValueTextSize(13f);
        lineData2.setValueTextColor(Color.BLACK);


        lineChart.setData(lineData);
        lineChart.invalidate();

        lineChartPredkosc.setData(lineData2);
        lineChartPredkosc.invalidate();


        return rootView;
    }



    public void onDestroy() {
        if(zb != null)
            zb.close();
        super.onDestroy();
    }
}
