/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.BelgeTipi;
import com.ronin.model.constant.Blok;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author msevim
 */
public class BelgeSorguKriteri implements Serializable{

    private BelgeTipi belgeTipi;
    private String belgeAdi;
    private Date sorguBaslangicTarihi;
    private Date sorguBitisTarihi;
    private Blok blok;
    private Integer daireNo;
    private Kullanici kullanici;

    public Date getSorguBitisTarihi() {
        return sorguBitisTarihi;
    }

    public void setSorguBitisTarihi(Date sorguBitisTarihi) {
        this.sorguBitisTarihi = sorguBitisTarihi;
    }

    public Date getSorguBaslangicTarihi() {
        return sorguBaslangicTarihi;
    }

    public void setSorguBaslangicTarihi(Date sorguBaslangicTarihi) {
        this.sorguBaslangicTarihi = sorguBaslangicTarihi;
    }

    public String getBelgeAdi() {
        return belgeAdi;
    }

    public void setBelgeAdi(String belgeAdi) {
        this.belgeAdi = belgeAdi;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public BelgeTipi getBelgeTipi() {
        return belgeTipi;
    }

    public void setBelgeTipi(BelgeTipi belgeTipi) {
        this.belgeTipi = belgeTipi;
    }
}
