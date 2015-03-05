/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.TalepOnayDurumu;
import com.ronin.model.constant.TalepTipi;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class TalepSorguKriteri implements Serializable{
    
    private Long talepNo;
    private Integer daireNo;
    private String blokAdi;
    private Kullanici kullanici;
    private TalepTipi talepTipi;
    private TalepOnayDurumu talepOnayDurumu;
    private String onayDurumu;
    private String daireKodu;
    private List<TalepTipi> talepTipiList;
    private Blok blok;

    private Date sorguBaslangicTarihi;
    private Date sorguBitisTarihi;


    public Long getTalepNo() {
        return talepNo;
    }

    public void setTalepNo(Long talepNo) {
        this.talepNo = talepNo;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
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

    public TalepTipi getTalepTipi() {
        return talepTipi;
    }

    public void setTalepTipi(TalepTipi talepTipi) {
        this.talepTipi = talepTipi;
    }

    public TalepOnayDurumu getTalepOnayDurumu() {
        return talepOnayDurumu;
    }

    public void setTalepOnayDurumu(TalepOnayDurumu talepOnayDurumu) {
        this.talepOnayDurumu = talepOnayDurumu;
    }

    public String getOnayDurumu() {
        return onayDurumu;
    }

    public void setOnayDurumu(String onayDurumu) {
        this.onayDurumu = onayDurumu;
    }

    public String getDaireKodu() {
        return daireKodu;
    }

    public void setDaireKodu(String daireKodu) {
        this.daireKodu = daireKodu;
    }

    public List<TalepTipi> getTalepTipiList() {
        return talepTipiList;
    }

    public void setTalepTipiList(List<TalepTipi> talepTipiList) {
        this.talepTipiList = talepTipiList;
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

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}
