/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.EvetHayir;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author msevim
 */
public class AnketSorguKriteri implements Serializable{
    
    private String anketKonusu;
    private Date sorguBaslangicTarihi;
    private Date sorguBitisTarihi;
    private EvetHayir anketAktifMi;

    public String getAnketKonusu() {
        return anketKonusu;
    }

    public void setAnketKonusu(String anketKonusu) {
        this.anketKonusu = anketKonusu;
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

    public EvetHayir getAnketAktifMi() {
        return anketAktifMi;
    }

    public void setAnketAktifMi(EvetHayir anketAktifMi) {
        this.anketAktifMi = anketAktifMi;
    }
}
