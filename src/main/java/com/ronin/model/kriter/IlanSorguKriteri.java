/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author msevim
 */
public class IlanSorguKriteri implements Serializable{
    
    private String konu;
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

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
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
}
