/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Rol;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;

import java.io.Serializable;

/**
 *
 * @author msevim
 */
public class KullaniciSorguKriteri implements Serializable{

    private String ad;
    private String soyad;
    private Integer tcid = null;
    private String kullaniciAdi;
    private Durum durum;
    private EvetHayir sistemYoneticisiMi;
    private Rol rol;
    private Integer daireNo;
    private String blokAdi;
    private String email;
    private Blok blok;


    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public Integer getTcid() {
        return tcid;
    }

    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public EvetHayir getSistemYoneticisiMi() {
        return sistemYoneticisiMi;
    }

    public void setSistemYoneticisiMi(EvetHayir sistemYoneticisiMi) {
        this.sistemYoneticisiMi = sistemYoneticisiMi;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}
