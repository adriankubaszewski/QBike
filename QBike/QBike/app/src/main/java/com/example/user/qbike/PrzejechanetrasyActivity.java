package com.example.user.qbike;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class PrzejechanetrasyActivity extends AppCompatActivity {

    //////// plik cache ////
    public static final String PREFS_NAME = "QBikePrefsFile";

    public ListView listaTras;
    public ArrayAdapter<String> adapter;
    private ObslugaBazy zb;

    double przejechanyDystans = 0.0;
    int przejechaneTrasy = 0;
    double predMaxMaxL = 0.0;

    String pom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przejechanetrasy);


        listaTras = (ListView) findViewById(R.id.listViewTrasy);

        final TextView textViewDystans = (TextView) findViewById(R.id.textViewWypiszDystans);
        final TextView textViewTrasy = (TextView) findViewById(R.id.textViewWypiszLiczbeTras);

        // nazwy plikow w folderze
        /*File Root = Environment.getExternalStorageDirectory();
        String[] elementy = new File(Root.getAbsolutePath() + "/MojeTrasy").list();*/
        //pliki.addAll( Arrays.asList(elementy) );

        ArrayList<String> listaTrasArray = new ArrayList<String>();
        final ArrayList<Integer> idTras = new ArrayList<>();

        zb = new ObslugaBazy(this);
        zb.open();

        String dystans = "";
        String predMaxMax ="";
        long id=0;

        for(TabelaTrasyOperacje k:zb.DB_WczytajWszystkieTrasy()){

            Log.d("dane:",k.getIdTrasy()+" "+k.getNazwaTrasy()+" "+k.getDataUtworzenia()+" "+k.getCzasTrwania()+" "
                    +k.getPredkoscSrednia());

            listaTrasArray.add(k.getNazwaTrasy()+" "+k.getDataUtworzenia()+" "+k.getCzasTrwania());

            pom=k.getPredkoscMax();
            predMaxMax = pom.replace(',', '.');
            double lpom = Double.parseDouble(predMaxMax);
            if(predMaxMaxL < lpom)
            {
                predMaxMaxL = lpom;
            }

            id=k.getIdTrasy();
            idTras.add((int)id);

            Long x = 100L;
            int y = x.intValue();

            pom=k.getDystans();
            dystans = pom.replace(',', '.');
            przejechanyDystans+= Double.parseDouble(dystans);
            przejechaneTrasy++;

        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        String dystZaokraglony = nf.format(przejechanyDystans);

        textViewDystans.setText(dystZaokraglony+" km");
        //textViewDystans.setText(dystans+" km");
        textViewTrasy.setText(przejechaneTrasy+"");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        if(przejechanyDystans<5){editor.putInt("Cache_Osiagniecie_Odl",0);}
        if(przejechanyDystans>=5&&przejechanyDystans<25){editor.putInt("Cache_Osiagniecie_Odl",1);}
        if(przejechanyDystans>=25&&przejechanyDystans<50){editor.putInt("Cache_Osiagniecie_Odl",2);}
        if(przejechanyDystans>=50&&przejechanyDystans<100){editor.putInt("Cache_Osiagniecie_Odl",3);}
        if(przejechanyDystans>=100&&przejechanyDystans<500){editor.putInt("Cache_Osiagniecie_Odl",4);}
        if(przejechanyDystans>=500&&przejechanyDystans<1000){editor.putInt("Cache_Osiagniecie_Odl",5);}
        if(przejechanyDystans>=1000){editor.putInt("Cache_Osiagniecie_Odl",6);}

        if(predMaxMaxL<15){editor.putInt("Cache_Osiagniecie_Pred",0);}
        if(predMaxMaxL>=15&&predMaxMaxL<25){editor.putInt("Cache_Osiagniecie_Pred",1);}
        if(predMaxMaxL>=25&&predMaxMaxL<30){editor.putInt("Cache_Osiagniecie_Pred",2);}
        if(predMaxMaxL>=30&&predMaxMaxL<35){editor.putInt("Cache_Osiagniecie_Pred",3);}
        if(predMaxMaxL>=35&&predMaxMaxL<40){editor.putInt("Cache_Osiagniecie_Pred",4);}
        if(predMaxMaxL>=40&&predMaxMaxL<50){editor.putInt("Cache_Osiagniecie_Pred",5);}
        if(predMaxMaxL>=50&&predMaxMaxL<70){editor.putInt("Cache_Osiagniecie_Pred",6);}
        if(predMaxMaxL>=70){editor.putInt("Cache_Osiagniecie_Pred",7);}


        editor.commit();


        adapter = new ArrayAdapter<String>(this, R.layout.elementlistydowyswietlenia, listaTrasArray);

        listaTras.setAdapter(adapter);

        listaTras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                Log.d("dane:","pos: "+ position+ " id:" + id);

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                //editor.putInt("OstatniaTrasa",position+1);
                editor.putInt("OstatniaTrasa",idTras.get(position));

                // Commit the edits!
                editor.commit();

                Intent myIntent = new Intent(getApplicationContext(), PrzegladanieTrasyActivity.class);
                startActivity(myIntent);
            }
        });

    }

    protected void onDestroy() {
        if(zb != null)
            zb.close();
        super.onDestroy();
    }
}
