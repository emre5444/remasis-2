package com.ronin.model.sorguSonucu;

import com.ronin.model.constant.Blok;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 14.04.2014
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class R101SorguSonucu {

    String daireKodu;
    String blokAdi;
    Integer daireNo;
    Double bakiye;
    String malik;
    String sakin;
    private Blok blok;


    public String getDaireKodu() {
        return daireKodu;
    }

    public void setDaireKodu(String daireKodu) {
        this.daireKodu = daireKodu;
    }

    public String getBlokAdi() {
        return blokAdi;
    }

    public void setBlokAdi(String blokAdi) {
        this.blokAdi = blokAdi;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
    }

    public Double getBakiye() {
        return bakiye;
    }

    public void setBakiye(Double bakiye) {
        this.bakiye = bakiye;
    }

    public String getMalik() {
        return malik;
    }

    public void setMalik(String malik) {
        this.malik = malik;
    }

    public String getSakin() {
        return sakin;
    }

    public void setSakin(String sakin) {
        this.sakin = sakin;
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}
