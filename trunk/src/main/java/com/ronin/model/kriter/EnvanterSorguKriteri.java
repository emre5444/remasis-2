/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.model.Kategori;

import java.io.Serializable;
import java.util.Date;


public class EnvanterSorguKriteri implements Serializable{

    private String barkodNo;

    private Kategori kategori;

    private String marka;

    private String model;

    private String urunAdi;

    private String saticiFirma;

    private String zimmetliPersonel;

    private Date baslangicIlimTarihi;

    private Date bitisIlimTarihi;

    public String getBarkodNo() {
        return barkodNo;
    }

    public void setBarkodNo(String barkodNo) {
        this.barkodNo = barkodNo;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getSaticiFirma() {
        return saticiFirma;
    }

    public void setSaticiFirma(String saticiFirma) {
        this.saticiFirma = saticiFirma;
    }

    public String getZimmetliPersonel() {
        return zimmetliPersonel;
    }

    public void setZimmetliPersonel(String zimmetliPersonel) {
        this.zimmetliPersonel = zimmetliPersonel;
    }

    public Date getBaslangicIlimTarihi() {
        return baslangicIlimTarihi;
    }

    public void setBaslangicIlimTarihi(Date baslangicIlimTarihi) {
        this.baslangicIlimTarihi = baslangicIlimTarihi;
    }

    public Date getBitisIlimTarihi() {
        return bitisIlimTarihi;
    }

    public void setBitisIlimTarihi(Date bitisIlimTarihi) {
        this.bitisIlimTarihi = bitisIlimTarihi;
    }
}
