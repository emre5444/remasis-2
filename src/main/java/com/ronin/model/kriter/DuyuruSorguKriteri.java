/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author msevim
 */
public class DuyuruSorguKriteri implements Serializable{
    
    private String konu;
    private Date sorguBaslangicTarihi;
    private Date sorguBitisTarihi;

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
}
