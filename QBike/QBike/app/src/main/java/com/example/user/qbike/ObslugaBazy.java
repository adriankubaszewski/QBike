/**
 * Created by user on 2017-08-16.
 */

package com.example.user.qbike;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ObslugaBazy{

    private static final String DEBUG_TAG = "SqLiteBaza";

    //// Dane bazy danych
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dbQBIKE.db";
    private static final String DB_TABELA_TRASY = "Trasy";
    private static final String DB_TABELA_PUNKTY_TRASY = "PunktyTrasy";

    ///////////////////
    // Pola bazy danych
    ///////////////////

    ///////////////////
    // TABELA TRASY

    // ID_TRASY
    public static final String TRASY_KOLUMNA_ID_TRASA = "ID_Trasy";
    public static final String TRASY_ID_TRASA_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int TRASY_ID_TRASA_COLUMN = 0;

    // NAZWA_TRASY
    public static final String TRASY_KOLUMNA_NAZWA_TRASY = "NazwaTrasy";
    public static final String TRASY_NAZWA_TRASY_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_NAZWA_TRASY_COLUMN = 1;

    // DATA_UTWORZENIA
    public static final String TRASY_KOLUMNA_DATA_UTWORZENIA = "DataUtworzenia";
    public static final String TRASY_DATA_UTWORZENIA_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_DATA_UTWORZENIA_COLUMN = 2;

    // CZAS TRWANIA
    public static final String TRASY_KOLUMNA_CZAS_TRWANIA = "CzasTrwania";
    public static final String TRASY_CZAS_TRWANIA_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_CZAS_TRWANIA_COLUMN = 3;

    // DYSTANS
    public static final String TRASY_KOLUMNA_DYSTANS = "Dystans";
    public static final String TRASY_DYSTANS_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_DYSTANS_COLUMN = 4;

    // PREDKOSC_SREDNIA
    public static final String TRASY_KOLUMNA_PREDKOSC_SREDNIA = "PredkoscSrednia";
    public static final String TRASY_PREDKOSC_SREDNIA_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_PREDKOSC_SREDNIA_COLUMN = 5;

    // PREDKOSC_MAX
    public static final String TRASY_KOLUMNA_PREDKOSC_MAX = "PredkoscMax";
    public static final String TRASY_PREDKOSC_MAX_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_PREDKOSC_MAX_COLUMN = 6;

    // SPALONE_KCAL
    public static final String TRASY_KOLUMNA_SPALONE_KCAL = "SpaloneKCAL";
    public static final String TRASY_SPALONE_KCAL_OPTIONS = "TEXT NOT NULL";
    public static final int TRASY_SPALONE_KCAL_COLUMN = 7;


    ///////////////////
    // TABELA PUNKTY TRASY

    // ID_PUNKTU
    public static final String PUNKTY_KOLUMNA_ID_PUNKTTRASY = "ID_PunktTrasy";
    public static final String PUNKTY_ID_PUNKTTRASY_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int PUNKTY_ID_PUNKTTRASY_COLUMN = 0;

    // DLUGOSC GEOGRAFICZNA
    public static final String PUNKTY_KOLUMNA_DLGEO = "DlugoscGeograficzna";
    public static final String PUNKTY_DLGEO_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_DLGEO_COLUMN = 1;

    // SZEROKOSC GEOGRAFICZNA
    public static final String PUNKTY_KOLUMNA_SZERGEO = "SzerokoscGeograficzna";
    public static final String PUNKTY_SZERGEO_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_SZERGEO_COLUMN = 2;

    // WYSOKOSC
    public static final String PUNKTY_KOLUMNA_WYSOKOSC = "Wysokosc";
    public static final String PUNKTY_WYSOKOSC_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_WYSOKOSC_COLUMN = 3;

    // CZAS PUNKTU
    public static final String PUNKTY_KOLUMNA_CZAS_PUNKTU = "CzasPunktu";
    public static final String PUNKTY_CZAS_PUNKTU_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_CZAS_PUNKTU_COLUMN = 4;

    // DATA PUNKTU
    public static final String PUNKTY_KOLUMNA_DATA_PUNKTU = "DataPunktu";
    public static final String PUNKTY_DATA_PUNKTU_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_DATA_PUNKTU_COLUMN = 5;

    // PREDKOSC PUNKTU
    public static final String PUNKTY_KOLUMNA_PREDKOSC_PUNKTU = "PredkoscPunktu";
    public static final String PUNKTY_PREDKOSC_PUNKTU_OPTIONS = "TEXT NOT NULL";
    public static final int PUNKTY_PREDKOSC_PUNKTU_COLUMN = 6;

    // IDTRASY - KLUCZ OBCY
    public static final String PUNKTY_KOLUMNA_IDTRASY = "IDTrasy";
    public static final String PUNKTY_IDTRASY_OPTIONS = "INTEGER NOT NULL";
    public static final int PUNKTY_IDTRASY_COLUMN = 7;


    //////////////////
    /// Tworzenie bazy

    private static final String DB_UTWORZ_TABELE_TRASY =
            "CREATE TABLE " + DB_TABELA_TRASY + "( " +
                    TRASY_KOLUMNA_ID_TRASA + " " + TRASY_ID_TRASA_OPTIONS + ", " +
                    TRASY_KOLUMNA_NAZWA_TRASY + " " + TRASY_NAZWA_TRASY_OPTIONS + ", " +
                    TRASY_KOLUMNA_DATA_UTWORZENIA + " " + TRASY_DATA_UTWORZENIA_OPTIONS + ", " +
                    TRASY_KOLUMNA_CZAS_TRWANIA + " " + TRASY_CZAS_TRWANIA_OPTIONS + ", " +
                    TRASY_KOLUMNA_DYSTANS + " " + TRASY_DYSTANS_OPTIONS + ", " +
                    TRASY_KOLUMNA_PREDKOSC_SREDNIA + " " + TRASY_PREDKOSC_SREDNIA_OPTIONS + ", " +
                    TRASY_KOLUMNA_PREDKOSC_MAX + " " + TRASY_PREDKOSC_MAX_OPTIONS + ", " +
                    TRASY_KOLUMNA_SPALONE_KCAL + " " + TRASY_SPALONE_KCAL_OPTIONS +
                    ");";

    private static final String DB_UTWORZ_TABELE_PUNKTY_TRASY =
            "CREATE TABLE " + DB_TABELA_PUNKTY_TRASY + "( " +
                    PUNKTY_KOLUMNA_ID_PUNKTTRASY + " " + PUNKTY_ID_PUNKTTRASY_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_DLGEO + " " + PUNKTY_DLGEO_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_SZERGEO + " " + PUNKTY_SZERGEO_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_WYSOKOSC + " " + PUNKTY_WYSOKOSC_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_CZAS_PUNKTU + " " + PUNKTY_CZAS_PUNKTU_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_DATA_PUNKTU + " " + PUNKTY_DATA_PUNKTU_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_PREDKOSC_PUNKTU + " " + PUNKTY_PREDKOSC_PUNKTU_OPTIONS + ", " +
                    PUNKTY_KOLUMNA_IDTRASY + " " + PUNKTY_IDTRASY_OPTIONS +
                    ");";

    //////////////////
    /// Usuwanie bazy

    private static final String DB_USUN_TABELE_TRASY = "DROP TABLE IF EXISTS " + DB_TABELA_TRASY;



    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_UTWORZ_TABELE_TRASY);
            db.execSQL(DB_UTWORZ_TABELE_PUNKTY_TRASY);

            Log.d(DEBUG_TAG, "Tworzenie bazy...");
            Log.d(DEBUG_TAG, "Tabela " + DB_TABELA_TRASY + " ver." + DB_VERSION + " utworzona");
            Log.d(DEBUG_TAG, "Tabela " + DB_TABELA_PUNKTY_TRASY + " ver." + DB_VERSION + " utworzona");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_USUN_TABELE_TRASY);
            db.execSQL(DB_UTWORZ_TABELE_PUNKTY_TRASY);

            Log.d(DEBUG_TAG, "Aktualizowanie bazy...");
            Log.d(DEBUG_TAG, "Tabela " + DB_TABELA_TRASY + " aktualizacja z wersji " + oldVersion + " do" + newVersion);
            Log.d(DEBUG_TAG, "Tabela " + DB_TABELA_PUNKTY_TRASY + " aktualizacja z wersji " + oldVersion + " do" + newVersion);
            Log.d(DEBUG_TAG, "Wszystkie dane utracone");

            onCreate(db);
        }

    }

    public ObslugaBazy(Context context) {
        this.context = context;
    }

    public ObslugaBazy open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    ////////////
    // Zamykanie bazy danych
    public void close() {
        dbHelper.close();
    }


    /////////////
    /// Operacje na tabeli


    public long DB_DodajTrase(String nazwatrasy, String DataUtworzenia, String CzasTrwania, String dystans, String PredkoscSrednia, String PredkoscMax, String SpaloneKCAL) {
        ContentValues nowaTrasa = new ContentValues();
        nowaTrasa.put(TRASY_KOLUMNA_NAZWA_TRASY, nazwatrasy);
        nowaTrasa.put(TRASY_KOLUMNA_DATA_UTWORZENIA, DataUtworzenia);
        nowaTrasa.put(TRASY_KOLUMNA_CZAS_TRWANIA, CzasTrwania);
        nowaTrasa.put(TRASY_KOLUMNA_DYSTANS, dystans);
        nowaTrasa.put(TRASY_KOLUMNA_PREDKOSC_SREDNIA, PredkoscSrednia);
        nowaTrasa.put(TRASY_KOLUMNA_PREDKOSC_MAX, PredkoscMax);
        nowaTrasa.put(TRASY_KOLUMNA_SPALONE_KCAL, SpaloneKCAL);
        return db.insert(DB_TABELA_TRASY, null, nowaTrasa);
    }


    public long DB_DodajPunktTrasy(String DlugoscGeo,String SzerokoscGeo,String Wysokosc,String CzasPunktu,String DataPunktu,
                                   String PredkoscPunktu,long idTrasy) {
        ContentValues nowyPunkt = new ContentValues();
        nowyPunkt.put(PUNKTY_KOLUMNA_DLGEO, DlugoscGeo);
        nowyPunkt.put(PUNKTY_KOLUMNA_SZERGEO, SzerokoscGeo);
        nowyPunkt.put(PUNKTY_KOLUMNA_WYSOKOSC, Wysokosc);
        nowyPunkt.put(PUNKTY_KOLUMNA_CZAS_PUNKTU, CzasPunktu);
        nowyPunkt.put(PUNKTY_KOLUMNA_DATA_PUNKTU, DataPunktu);
        nowyPunkt.put(PUNKTY_KOLUMNA_PREDKOSC_PUNKTU, PredkoscPunktu);
        nowyPunkt.put(PUNKTY_KOLUMNA_IDTRASY, idTrasy);
        return db.insert(DB_TABELA_PUNKTY_TRASY, null, nowyPunkt);
    }


    public List<TabelaTrasyOperacje> DB_WczytajWszystkieTrasy(){
        List<TabelaTrasyOperacje> Trasy = new LinkedList<TabelaTrasyOperacje>();
        String[] columns = {TRASY_KOLUMNA_ID_TRASA, TRASY_KOLUMNA_NAZWA_TRASY, TRASY_KOLUMNA_DATA_UTWORZENIA,
                TRASY_KOLUMNA_CZAS_TRWANIA,TRASY_KOLUMNA_DYSTANS,TRASY_KOLUMNA_PREDKOSC_SREDNIA,TRASY_KOLUMNA_PREDKOSC_MAX,
                TRASY_KOLUMNA_SPALONE_KCAL};
        //dbHelper.getReadableDatabase();
        Cursor kursor = db.query(DB_TABELA_TRASY, columns, null, null, null, null, null);
        while(kursor.moveToNext())
        {
            TabelaTrasyOperacje TabTrasy = new TabelaTrasyOperacje();
            TabTrasy.setIdTrasy(kursor.getLong(TRASY_ID_TRASA_COLUMN));
            TabTrasy.setNazwaTrasy(kursor.getString(TRASY_NAZWA_TRASY_COLUMN));
            TabTrasy.setDataUtworzenia(kursor.getString(TRASY_DATA_UTWORZENIA_COLUMN));
            TabTrasy.setCzasTrwania(kursor.getString(TRASY_CZAS_TRWANIA_COLUMN));
            TabTrasy.setDystans(kursor.getString(TRASY_DYSTANS_COLUMN));
            TabTrasy.setPredkoscSrednia(kursor.getString(TRASY_PREDKOSC_SREDNIA_COLUMN));
            TabTrasy.setPredkoscMax(kursor.getString(TRASY_PREDKOSC_MAX_COLUMN));
            TabTrasy.setSpaloneKcal(kursor.getString(TRASY_SPALONE_KCAL_COLUMN));
            Trasy.add(TabTrasy);
        }
        return Trasy;
    }

    public TabelaTrasyOperacje DB_WczytajTrase(int idTrasy){
        TabelaTrasyOperacje TabTrasy = new TabelaTrasyOperacje();
        String[] columns = {TRASY_KOLUMNA_ID_TRASA, TRASY_KOLUMNA_NAZWA_TRASY, TRASY_KOLUMNA_DATA_UTWORZENIA,
                TRASY_KOLUMNA_CZAS_TRWANIA,TRASY_KOLUMNA_DYSTANS,TRASY_KOLUMNA_PREDKOSC_SREDNIA,TRASY_KOLUMNA_PREDKOSC_MAX,
                TRASY_KOLUMNA_SPALONE_KCAL};
        String args[] = {idTrasy+""};
        Cursor kursor = db.query(DB_TABELA_TRASY, columns, " ID_Trasy=?", args, null, null, null);
        if(kursor!=null){
            kursor.moveToFirst();
            TabTrasy.setIdTrasy(kursor.getLong(TRASY_ID_TRASA_COLUMN));
            TabTrasy.setNazwaTrasy(kursor.getString(TRASY_NAZWA_TRASY_COLUMN));
            TabTrasy.setDataUtworzenia(kursor.getString(TRASY_DATA_UTWORZENIA_COLUMN));
            TabTrasy.setCzasTrwania(kursor.getString(TRASY_CZAS_TRWANIA_COLUMN));
            TabTrasy.setDystans(kursor.getString(TRASY_DYSTANS_COLUMN));
            TabTrasy.setPredkoscSrednia(kursor.getString(TRASY_PREDKOSC_SREDNIA_COLUMN));
            TabTrasy.setPredkoscMax(kursor.getString(TRASY_PREDKOSC_MAX_COLUMN));
            TabTrasy.setSpaloneKcal(kursor.getString(TRASY_SPALONE_KCAL_COLUMN));
        }
        return TabTrasy;
    }

    public void DB_UsunTrase(int idTrasy)
    {
        String[] argumenty={""+idTrasy};
        db.delete(DB_TABELA_TRASY,"ID_Trasy=?",argumenty);
    }

    public void DB_EdytujTrase(int IDTrasy, String nazwatrasy, String DataUtworzenia, String CzasTrwania, String dystans, String PredkoscSrednia, String PredkoscMax, String SpaloneKCAL)
    {
        ContentValues edytowanaTrasa = new ContentValues();
        edytowanaTrasa.put(TRASY_KOLUMNA_NAZWA_TRASY, nazwatrasy);
        edytowanaTrasa.put(TRASY_KOLUMNA_DATA_UTWORZENIA, DataUtworzenia);
        edytowanaTrasa.put(TRASY_KOLUMNA_CZAS_TRWANIA, CzasTrwania);
        edytowanaTrasa.put(TRASY_KOLUMNA_DYSTANS, dystans);
        edytowanaTrasa.put(TRASY_KOLUMNA_PREDKOSC_SREDNIA, PredkoscSrednia);
        edytowanaTrasa.put(TRASY_KOLUMNA_PREDKOSC_MAX, PredkoscMax);
        edytowanaTrasa.put(TRASY_KOLUMNA_SPALONE_KCAL, SpaloneKCAL);
        String args[] = {IDTrasy+""};
        db.update(DB_TABELA_TRASY, edytowanaTrasa, "ID_Trasy=?",args);
    }

    /*public Cursor DB_GetWszystkieTrasy() {
        String[] columns = {TRASY_KOLUMNA_ID_TRASA, TRASY_KOLUMNA_NAZWA_TRASY, TRASY_KOLUMNA_DATA_UTWORZENIA,
                TRASY_KOLUMNA_CZAS_TRWANIA,TRASY_KOLUMNA_DYSTANS,TRASY_KOLUMNA_PREDKOSC_SREDNIA,TRASY_KOLUMNA_PREDKOSC_MAX,
                TRASY_KOLUMNA_SPALONE_KCAL};
        return db.query(DB_TABELA_TRASY, columns, null, null, null, null, null);
    }*/

    public List<TabelaPunktyTrasyOperacje> DB_WczytajPunktyTrasy(int idTrasy){
        List<TabelaPunktyTrasyOperacje> PunktyTrasy = new LinkedList<TabelaPunktyTrasyOperacje>();
        String[] columns = {PUNKTY_KOLUMNA_ID_PUNKTTRASY, PUNKTY_KOLUMNA_DLGEO, PUNKTY_KOLUMNA_SZERGEO, PUNKTY_KOLUMNA_WYSOKOSC,
                PUNKTY_KOLUMNA_CZAS_PUNKTU,PUNKTY_KOLUMNA_DATA_PUNKTU,PUNKTY_KOLUMNA_PREDKOSC_PUNKTU,PUNKTY_KOLUMNA_IDTRASY};
        String args[] = {idTrasy+""};
        Cursor kursor = db.query(DB_TABELA_PUNKTY_TRASY, columns, " IDTrasy=?", args, null, null, null);
        while(kursor.moveToNext())
        {
            TabelaPunktyTrasyOperacje PunktTrasy = new TabelaPunktyTrasyOperacje();
            PunktTrasy.setIDPunktTrasy(kursor.getLong(PUNKTY_ID_PUNKTTRASY_COLUMN));
            PunktTrasy.setDlugoscGeo(kursor.getString(PUNKTY_DLGEO_COLUMN));
            PunktTrasy.setSzerokoscGeo(kursor.getString(PUNKTY_SZERGEO_COLUMN));
            PunktTrasy.setWysokosc(kursor.getString(PUNKTY_WYSOKOSC_COLUMN));
            PunktTrasy.setCzasPunktu(kursor.getString(PUNKTY_CZAS_PUNKTU_COLUMN));
            PunktTrasy.setDataPunktu(kursor.getString(PUNKTY_DATA_PUNKTU_COLUMN));
            PunktTrasy.setPredkoscPunktu(kursor.getString(PUNKTY_PREDKOSC_PUNKTU_COLUMN));
            PunktTrasy.setIdTrasy(kursor.getLong(PUNKTY_IDTRASY_COLUMN));
            PunktyTrasy.add(PunktTrasy);
        }
        return PunktyTrasy;

    }

    public List<TabelaPunktyTrasyOperacje> DB_WczytajWszystkiePunkty(){
        List<TabelaPunktyTrasyOperacje> PunktyTrasy = new LinkedList<TabelaPunktyTrasyOperacje>();
        String[] columns = {PUNKTY_KOLUMNA_ID_PUNKTTRASY, PUNKTY_KOLUMNA_DLGEO, PUNKTY_KOLUMNA_SZERGEO, PUNKTY_KOLUMNA_WYSOKOSC,
                PUNKTY_KOLUMNA_CZAS_PUNKTU,PUNKTY_KOLUMNA_DATA_PUNKTU,PUNKTY_KOLUMNA_PREDKOSC_PUNKTU,PUNKTY_KOLUMNA_IDTRASY};
        //dbHelper.getReadableDatabase();
        Cursor kursor = db.query(DB_TABELA_PUNKTY_TRASY, columns, null, null, null, null, null);
        while(kursor.moveToNext())
        {
            TabelaPunktyTrasyOperacje PunktTrasy = new TabelaPunktyTrasyOperacje();
            PunktTrasy.setIDPunktTrasy(kursor.getLong(PUNKTY_ID_PUNKTTRASY_COLUMN));
            PunktTrasy.setDlugoscGeo(kursor.getString(PUNKTY_DLGEO_COLUMN));
            PunktTrasy.setSzerokoscGeo(kursor.getString(PUNKTY_SZERGEO_COLUMN));
            PunktTrasy.setWysokosc(kursor.getString(PUNKTY_WYSOKOSC_COLUMN));
            PunktTrasy.setCzasPunktu(kursor.getString(PUNKTY_CZAS_PUNKTU_COLUMN));
            PunktTrasy.setDataPunktu(kursor.getString(PUNKTY_DATA_PUNKTU_COLUMN));
            PunktTrasy.setPredkoscPunktu(kursor.getString(PUNKTY_PREDKOSC_PUNKTU_COLUMN));
            PunktTrasy.setIdTrasy(kursor.getLong(PUNKTY_IDTRASY_COLUMN));

            PunktyTrasy.add(PunktTrasy);
        }
        return PunktyTrasy;
    }

    public void DB_UsunPunkty(int idTrasy)
    {
        String[] argumenty={""+idTrasy};
        db.delete(DB_TABELA_PUNKTY_TRASY,"IDTrasy=?",argumenty);
    }



}