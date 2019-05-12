package com.example.user.qbike;

/**
 * Created by user on 2017-12-05.
 */


public class TabelaPunktyTrasyOperacje {

    public static final String TAG = "PunktTrasy";


    private long IDPunktTrasy;
    private String DlugoscGeo;
    private String SzerokoscGeo;
    private String Wysokosc;
    private String CzasPunktu;
    private String DataPunktu;
    private String PredkoscPunktu;

    /// klucz obcy
    private long idTrasy;


    /*public TabelaPunktyTrasyOperacje(long idPunktTras, String DlGeo, String SzerGeo, String Wys, String CzasPkt, String DataPkt, String PredPkt) {

        this.IDPunktTrasy=idPunktTras;
        this.DlugoscGeo=DlGeo;
        this.SzerokoscGeo=SzerGeo;
        this.Wysokosc=Wys;
        this.CzasPunktu=CzasPkt;
        this.DataPunktu=DataPkt;
        this.PredkoscPunktu=PredPkt;
    }*/

    public long getIDPunktTrasy() {
        return IDPunktTrasy;
    }

    public void setIDPunktTrasy(long IDPunktTrasy) {
        this.IDPunktTrasy = IDPunktTrasy;
    }

    public String getDlugoscGeo() {
        return DlugoscGeo;
    }

    public void setDlugoscGeo(String dlugoscGeo) {
        DlugoscGeo = dlugoscGeo;
    }

    public String getSzerokoscGeo() {
        return SzerokoscGeo;
    }

    public void setSzerokoscGeo(String szerokoscGeo) {
        SzerokoscGeo = szerokoscGeo;
    }

    public String getWysokosc() {
        return Wysokosc;
    }

    public void setWysokosc(String wysokosc) {
        Wysokosc = wysokosc;
    }

    public String getCzasPunktu() {
        return CzasPunktu;
    }

    public void setCzasPunktu(String czasPunktu) {
        CzasPunktu = czasPunktu;
    }

    public String getDataPunktu() {
        return DataPunktu;
    }

    public void setDataPunktu(String dataPunktu) {
        DataPunktu = dataPunktu;
    }

    public String getPredkoscPunktu() {
        return PredkoscPunktu;
    }

    public void setPredkoscPunktu(String predkoscPunktu) {
        PredkoscPunktu = predkoscPunktu;
    }

    public long getIdTrasy() {
        return idTrasy;
    }

    public void setIdTrasy(long idTrasy) {
        this.idTrasy = idTrasy;
    }



}
