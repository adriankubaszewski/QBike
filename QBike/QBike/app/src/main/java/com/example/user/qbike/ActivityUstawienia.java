package com.example.user.qbike;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityUstawienia extends AppCompatActivity {

    public static final String PREFS_NAME = "QBikePrefsFile";

    String odczytanaNowaNazwa="";
    EditText PredTrening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        final EditText editTextWaga = (EditText) findViewById(R.id.editTextWaga);
        final EditText editTextWzrost = (EditText) findViewById(R.id.editTextWzrost);
        final SeekBar seekBarOdswiezanie = (SeekBar) findViewById(R.id.seekBarCzasOdswiezania);
        final SeekBar seekBarDokladnosc = (SeekBar) findViewById(R.id.seekBarDokladnoscPomiaru);
        final SeekBar seekBarPrzyblizenie  = (SeekBar) findViewById(R.id.seekBarZoomMapy);
        final Switch switchTrening = (Switch) findViewById(R.id.switchTrening);
        final EditText editTextTreningPred = (EditText) findViewById(R.id.editTextTreningPredSr);
        final EditText editTextTreningDystans = (EditText) findViewById(R.id.editTextTreningDystans);


        /////////////////// Pobieranie z cache
        SharedPreferences settings =  getSharedPreferences(PREFS_NAME, 0);
        //int ostatniNrPliku = settings.getInt("nrPliku", 5);
        int CacheWaga = settings.getInt("Waga",50);
        int CacheWzrost = settings.getInt("Wzrost",175);
        int CacheOdswiezanie = settings.getInt("Odswiezanie",3000);
        int CacheDokladnosc = settings.getInt("Dokladnosc",3);
        int CachePrzyblizenie = settings.getInt("Zoom",12);
        boolean CacheTrening = settings.getBoolean("Trening",false);
        int CacheTreningPredkosc = settings.getInt("TreningPred",20);
        int CacheTreningOdleglosc = settings.getInt("TreningOdleglosc",1000);


        ////////////////////


        editTextWaga.setText(CacheWaga+"");
        editTextWzrost.setText(CacheWzrost+"");
        seekBarOdswiezanie.setProgress(CacheOdswiezanie/1000);
        seekBarDokladnosc.setProgress(CacheDokladnosc);
        seekBarPrzyblizenie.setProgress(CachePrzyblizenie-10);
        switchTrening.setChecked(CacheTrening);


        editTextTreningPred.setText(CacheTreningPredkosc+"");
        editTextTreningDystans.setText(CacheTreningOdleglosc+"");

        if(CacheTrening==false)
        {
            editTextTreningPred.setEnabled(false);
            editTextTreningDystans.setEnabled(false);

        }


        ///// zapis cache
        final Button button = (Button) findViewById(R.id.buttonZatwierdz);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putInt("Waga",Integer.parseInt(editTextWaga.getText().toString()));
                editor.putInt("Wzrost",Integer.parseInt(editTextWzrost.getText().toString()));
                editor.putInt("Odswiezanie",seekBarOdswiezanie.getProgress()*1000);
                editor.putInt("Dokladnosc",seekBarDokladnosc.getProgress());
                editor.putInt("Zoom",seekBarPrzyblizenie.getProgress()+10);
                editor.putBoolean("Trening",switchTrening.isChecked());
                editor.putInt("TreningPred",Integer.parseInt(editTextTreningPred.getText().toString()));
                editor.putInt("TreningOdleglosc",Integer.parseInt(editTextTreningDystans.getText().toString()));

                // Commit the edits!
                editor.commit();

                Context context = getApplicationContext();
                CharSequence text = "Zapisano zmiany";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

        switchTrening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true){
                    editTextTreningPred.setEnabled(true);
                    editTextTreningDystans.setEnabled(true);

                }else{
                    editTextTreningPred.setEnabled(false);
                    editTextTreningDystans.setEnabled(false);
                }
            }
        });

    }

}
