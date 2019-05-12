package com.example.user.qbike;

/**
 * Created by user on 2017-12-05.
 */

public class TabelaTrasyOperacje {

    private long idTrasy;
    private String nazwaTrasy;
    private String dataUtworzenia;
    private String czasTrwania;
    private String dystans;
    private String predkoscSrednia;
    private String predkoscMax;
    private String spaloneKcal;


    /*public TabelaTrasyOperacje(long idTras, String nazwaTras, String dataUtworz, String czasTrw, String dyst, String predSred, String predMax, String Kcal) {

        this.idTrasy=idTras;
        this.nazwaTrasy=nazwaTras;
        this.dataUtworzenia=dataUtworz;
        this.czasTrwania=czasTrw;
        this.dystans=dyst;
        this.predkoscSrednia=predSred;
        this.predkoscMax=predMax;
        this.spaloneKcal=Kcal;
    }*/


    public long getIdTrasy() {
        return idTrasy;
    }

    public void setIdTrasy(long idTrasy) {
        this.idTrasy = idTrasy;
    }

    public String getNazwaTrasy() {
        return nazwaTrasy;
    }

    public void setNazwaTrasy(String nazwaTrasy) {
        this.nazwaTrasy = nazwaTrasy;
    }

    public String getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(String dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }

    public String getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(String czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public String getDystans() {
        return dystans;
    }

    public void setDystans(String dystans) {
        this.dystans = dystans;
    }

    public String getPredkoscSrednia() {
        return predkoscSrednia;
    }

    public void setPredkoscSrednia(String predkoscSrednia) {
        this.predkoscSrednia = predkoscSrednia;
    }

    public String getPredkoscMax() {
        return predkoscMax;
    }

    public void setPredkoscMax(String predkoscMax) {
        this.predkoscMax = predkoscMax;
    }

    public String getSpaloneKcal() {
        return spaloneKcal;
    }

    public void setSpaloneKcal(String spaloneKcal) {
        this.spaloneKcal = spaloneKcal;
    }
}
