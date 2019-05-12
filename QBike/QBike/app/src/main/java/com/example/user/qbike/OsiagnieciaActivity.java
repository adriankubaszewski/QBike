package com.example.user.qbike;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OsiagnieciaActivity extends AppCompatActivity {

    ListView listaOsiagniec;

    public static final String PREFS_NAME = "QBikePrefsFile";

    int[] obrazki = new int [13];


    String[] osiagniecia = {"Przejedź 5 km",
            "Przejedź 25 km",
            "Przejedź 50 km",
            "Przejedź 100 km",
            "Przejedź 500 km",
            "Przejedź 1000 km",
            "Osiągnij prędkość 15km/h",
            "Osiągnij prędkość 25km/h",
            "Osiągnij prędkość 30km/h",
            "Osiągnij prędkość 35km/h",
            "Osiągnij prędkość 40km/h",
            "Osiągnij prędkość 50km/h",
            "Osiągnij prędkość 70km/h"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osiagniecia);

        listaOsiagniec = (ListView) findViewById(R.id.listViewOsiagniecia);

        SharedPreferences settings =  getSharedPreferences(PREFS_NAME, 0);
        int CacheOsOdl = settings.getInt("Cache_Osiagniecie_Odl",0);
        int CacheOsPred = settings.getInt("Cache_Osiagniecie_Pred",0);

        for(int i = 1; i<=6 ;i++)
        {
            if(i<=CacheOsOdl)
            {
                obrazki[i-1] = R.drawable.ok;
            }
            else
            {
                obrazki[i-1] = R.drawable.falsz;
            }
        }

        for(int i = 7; i<=13 ;i++)
        {
            if(i-6<=CacheOsPred)
            {
                obrazki[i-1] = R.drawable.ok;
            }
            else
            {
                obrazki[i-1] = R.drawable.falsz;
            }
        }

        ElementAdapter elAd = new ElementAdapter();
        listaOsiagniec.setAdapter(elAd);
    }

    class ElementAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return  osiagniecia.length;
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            View view = getLayoutInflater().inflate(R.layout.elementlistyosiagniecia,null);

            ImageView imView = (ImageView) view.findViewById(R.id.imageViewOsiagniecia);
            TextView txtView = (TextView) view.findViewById(R.id.textViewOsiagniecie);

            imView.setImageResource(obrazki[position]);
            txtView.setText(osiagniecia[position]);

            return view;
        }
    }
}
