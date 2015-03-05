/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;

import java.io.Serializable;

/**
 *
 * @author ealtun
 */
public class RaporSorguKriteri implements Serializable{
    
    private Long id;
    private Integer daireNo = null;
    private Integer kat = null;
    private String blokAdi;
    private Kullanici kullanici;
    private Blok blok;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
    }

    public Integer getKat() {
        return kat;
    }

    public void setKat(Integer kat) {
        this.kat = kat;
    }

    public String getBlokAdi() {
        return blokAdi;
    }

    public void setBlokAdi(String blokAdi) {
        this.blokAdi = blokAdi;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}
