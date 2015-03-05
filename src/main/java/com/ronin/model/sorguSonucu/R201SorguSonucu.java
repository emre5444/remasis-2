package com.ronin.model.sorguSonucu;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 14.04.2014
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class R201SorguSonucu {

    String daireKodu;
    String blokAdi;
    Integer daireNo;
    String malik;
    String sakin;

    String dekontNo;
    Date islemTarihi;
    Date odemeVadesi;
    Date sonOdemeTarihi;
    Double borc;
    Double odenenTutar;

    String aciklama;
    String banka;
    String odemeTipi;

    Double daireBakiye;


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

    public String getDekontNo() {
        return dekontNo;
    }

    public void setDekontNo(String dekontNo) {
        this.dekontNo = dekontNo;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public Date getOdemeVadesi() {
        return odemeVadesi;
    }

    public void setOdemeVadesi(Date odemeVadesi) {
        this.odemeVadesi = odemeVadesi;
    }

    public Date getSonOdemeTarihi() {
        return sonOdemeTarihi;
    }

    public void setSonOdemeTarihi(Date sonOdemeTarihi) {
        this.sonOdemeTarihi = sonOdemeTarihi;
    }

    public Double getBorc() {
        return borc;
    }

    public void setBorc(Double borc) {
        this.borc = borc;
    }

    public Double getOdenenTutar() {
        return odenenTutar;
    }

    public void setOdenenTutar(Double odenenTutar) {
        this.odenenTutar = odenenTutar;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getBanka() {
        return banka;
    }

    public void setBanka(String banka) {
        this.banka = banka;
    }

    public Double getDaireBakiye() {
        return daireBakiye;
    }

    public void setDaireBakiye(Double daireBakiye) {
        this.daireBakiye = daireBakiye;
    }

    public String getOdemeTipi() {
        return odemeTipi;
    }

    public void setOdemeTipi(String odemeTipi) {
        this.odemeTipi = odemeTipi;
    }
}
