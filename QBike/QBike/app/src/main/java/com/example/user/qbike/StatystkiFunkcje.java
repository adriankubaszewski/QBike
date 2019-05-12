package com.example.user.qbike;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Environment;
import android.widget.Toast;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by user on 2017-08-12.
 */

public class StatystkiFunkcje {

    /// parametry

    double dystans = 0;
    double odcinek = 0;
    double odcinekDoPred = 0;
    int odczytanePredkosci = 0;
    int odczytanepunkty = 0;
    double predkosc = 0.0;
    double predkoscMax = 0.0;
    double sredniapredkosc = 0.0;
    double predkosciSrednie = 0.0;
    long czasodpoczatku;
    long czasKilometra = 0;
    double czasruchuprzecinek = 0;
    long czasruchu=0;
    int minodleglosc = 2;
    double szer1=0;
    double dlug1=0;
    double szer2=0;
    double dlug2=0;
    double wysokosc=0;
    double kalorie=0;
    String szerzaokraglony;
    String dlugzaokraglony;

    Date dataprzed;
    Date datapo;
    Date czasstartu;
    Date czasKilometraTime;

    /// parametry z cache
    int czasodswiezania = 3000;
    int masa=100;
    int dokladnoscOdczytu = 3;
    int przyblizenieMapy = 12;
    int nrpliku = 0;
    int ostatniaTrasa = 0;

    double odcinekKilometr = 0.0;


    String pred2widok;

    List<String> listapozycji = new ArrayList<String>();

    Location poprzednialokacja = new Location("locationA");

    public void ustawNowaLokacja(double szer, double dlug, double wys)
    {
        szer1 = szer; // szer geo
        dlug1 = dlug; // dl geo
        wysokosc = wys; //wysokosc

        // zaokraglanie do 12 miejsc
        NumberFormat ns = NumberFormat.getInstance();
        ns.setMaximumFractionDigits(12);

        szerzaokraglony = ns.format(szer1);
        dlugzaokraglony = ns.format(dlug1);
    }

    public String obecnaGodzina()
    {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }

    /*public String obecnyCzas()
    {
        /*Timer timer = new Timer();
        timer.schedule(obliczCzasOdStartu(), 0, 1000);
        return czasodpoczatku;
    }*/

    public void obliczCzasOdStartu()
    {
        czasodpoczatku = (datapo.getTime()-czasstartu.getTime())/1000;
    }

    public void obliczCzasRuchu()
    {
        czasruchu = (datapo.getTime()-dataprzed.getTime());
    }

    public String wypiszCzasRuchu()
    {
        return Long.toString(czasruchu);
    }

    public void obliczPredkosc(float pobranaPredkosc)
    {
        //predkosc = odcinek*3.6/czasodswiezania*1000;
        //predkosc = odcinek/(czasodswiezania/3)*3.6;

        czasruchuprzecinek = czasruchu/1000;
        //predkosc = (odcinek*3.6)/czasruchuprzecinek;
        //predkosciSrednie += predkosc;

        predkosc = pobranaPredkosc*3.6;
        predkosciSrednie +=predkosc;

        //predkosc max
        if(predkosc>predkoscMax)
            predkoscMax = predkosc;
    }


    public void obliczPredkoscSred()
    {
        sredniapredkosc = predkosciSrednie/odczytanepunkty;
    }

    public void obliczKalorie()
    {
        if( predkosc<16)
        {

            kalorie += ((masa * 4 /60)*czasruchuprzecinek)/60;
        }
        else if(predkosc >=16 && predkosc<19)
        {
            kalorie += ((masa * 6 /60)*czasruchuprzecinek)/60;
            //kalorie += (masa * 6 /60)*czasruchuprzecinek;
        }
        else if(predkosc >=19 && predkosc<23)
        {
            kalorie += ((masa * 10 /60)*czasruchuprzecinek)/60;
            //kalorie += (masa * 10 /60)*czasruchuprzecinek;
        }
        else if(predkosc >=23 && predkosc<28)
        {
            //kalorie += (masa * 12 /60)*czasruchuprzecinek;
            kalorie += ((masa * 12 /60)*czasruchuprzecinek)/60;
        }
        else
        {
            //kalorie += (masa * 16 /60/60)*czasruchuprzecinek;
            //kalorie += masa * 16 /60* (czasruchuprzecinek/60);
            kalorie += ((masa * 16 /60)*czasruchuprzecinek)/60;
        }
    }

    public String wypiszKalorie()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String tekstKalorie = nf.format(kalorie);
        return tekstKalorie;
    }

    public String wypiszPredkoscSred()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String sredniapredzaokraglona = nf.format(sredniapredkosc);
        return sredniapredzaokraglona;
    }

    public String wypiszCzasOdStartu()
    {
        long godzina = czasodpoczatku/(60*60)%24;
        long minuta = czasodpoczatku/(60)%60;
        long sekunda = czasodpoczatku%60;

        String czastekst = godzina+":";

        if(minuta<10)
        {
            czastekst+="0"+minuta+":";
        }
        else
        {
            czastekst+=minuta+":";
        }
        if(sekunda<10)
        {
            czastekst+="0"+sekunda;
        }
        else
        {
            czastekst+=sekunda+"";
        }


        //String czastekst = (czasodpoczatku/(60*60)%24)+":"+(czasodpoczatku/(60)%60)+":"+(czasodpoczatku%60);
        return  czastekst;
    }

    public String wypiszPredMax()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String predmaxzaokraglona = nf.format(predkoscMax);
        return predmaxzaokraglona;
    }

    public String wypiszPredkosc()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String predzaokraglona = nf.format(predkosc);
        return predzaokraglona;
    }

    public String wypiszWysokosc()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String wysokosczaokraglona = nf.format(wysokosc);
        return wysokosczaokraglona;
    }

    public String wypiszWspolrzedne()
    {
        // dopelnienie wspolrzednej zerami
        String zera="000000000000000";

        String napis = szerzaokraglony+zera.substring(szerzaokraglony.length())+" "+dlugzaokraglony+zera.substring(dlugzaokraglony.length())
                +" "+obecnaGodzina()+"^"+wypiszWysokosc()+"#"+wypiszPredkosc()+"$";
        listapozycji.add(napis);

        return napis;
    }

    public String wypiszDystans()
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        String dystanszaokraglony = nf.format(dystans/1000);
        return dystanszaokraglony;
    }

    public String wypiszCzasOdcinka()
    {
        long czas = czasKilometra/1000;

        odcinekKilometr=0;
        czasKilometra=0;

        long godzina = czas/(60*60)%24;
        long minuta = czas/(60)%60;
        long sekunda = czas%60;

        String czastekst = godzina+":";

        if(minuta<10)
        {
            czastekst+="0"+minuta+":";
        }
        else
        {
            czastekst+=minuta+":";
        }
        if(sekunda<10)
        {
            czastekst+="0"+sekunda;
        }
        else
        {
            czastekst+=sekunda+"";
        }

        return  czastekst;
    }

    public String zapiszTraseDoPliku()
    {
        String state;
        state = Environment.getExternalStorageState();
        String tekstdozapisu = "";

        Date currentDate = new Date();
        SimpleDateFormat samadata = new SimpleDateFormat("yyyy-MM-dd");
        String Samadata = samadata.format(currentDate);
        SimpleDateFormat samczas = new SimpleDateFormat("HH:mm:ss");
        String Samczas = samczas.format(currentDate);

        char cs = '"';

        tekstdozapisu="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"QBike creator\">\n" +
                "<metadata>\n"+
                "<name>QBike creator</name>\n"+
                "<time>"+Samadata+"T"+Samczas+"Z</time>\n"+
                "</metadata>\n"+
                "<trk>\n"+
                "<trkseg>\n";

        for(int i =0; i< listapozycji.size();i++)
        {
            String s = (String)listapozycji.get(i);

            tekstdozapisu = tekstdozapisu + "<trkpt lat="+cs+s.substring(0,2)+"."+s.substring(3,15)+cs+" lon="+cs+s.substring(16,18)+"."+s.substring(19,30)+cs+">\n"+
                    "<ele>"+s.substring(s.indexOf("^")+1,s.indexOf("#"))+"</ele>\n"+
                    "<time>"+s.substring(32,42)+"T"+s.substring(43,51)+"Z</time>\n"+
                    "</trkpt>\n";
        }

        tekstdozapisu = tekstdozapisu + "</trkseg>\n" +
                "</trk>\n" +
                "</gpx>";


        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/MojeTrasy");
            if(!Dir.exists())
            {
                Dir.mkdir();
            }
            File file = new File(Dir,"MojaTrasa"+nrpliku+".gpx");

            String Message = tekstdozapisu;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();

                //nrpliku++;
                //listapozycji.clear();

                String powodzenie = "Zapisano";
                return powodzenie;
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return "0";
            } catch (IOException e) {
                e.printStackTrace();
                return "1";
            }
        }
        else
        {
            String blad = "Nie znaleziono karty SD";
            return blad;
            //Toast.makeText(getApplicationContext(), "Nie znaleziono karty SD", Toast.LENGTH_SHORT).show();
        }
    }

}
