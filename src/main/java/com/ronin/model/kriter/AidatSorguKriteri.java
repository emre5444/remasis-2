/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.KaynakTipi;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fcabi
 */
public class AidatSorguKriteri implements Serializable {

    private Blok blok;
    private Integer daireNo;
    private Kullanici kullanici;
    private Date sorguBaslangicTarihi;
    private Date sorguBitisTarihi;
    private KaynakTipi kaynakTipi;
    private String dekontNo;

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

    public Date getSorguBaslangicTarihi() {
        return sorguBaslangicTarihi;
    }

    public void setSorguBaslangicTarihi(Date sorguBaslangicTarihi) {
        this.sorguBaslangicTarihi = sorguBaslangicTarihi;
    }

    public Date getSorguBitisTarihi() {
        return sorguBitisTarihi;
    }

    public void setSorguBitisTarihi(Date sorguBitisTarihi) {
        this.sorguBitisTarihi = sorguBitisTarihi;
    }

    public KaynakTipi getKaynakTipi() {
        return kaynakTipi;
    }

    public void setKaynakTipi(KaynakTipi kaynakTipi) {
        this.kaynakTipi = kaynakTipi;
    }

    public String getDekontNo() {
        return dekontNo;
    }

    public void setDekontNo(String dekontNo) {
        this.dekontNo = dekontNo;
    }
}
