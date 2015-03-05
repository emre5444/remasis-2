/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.DaireTipi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class DaireSorguKriteri implements Serializable {

    private Long id;
    private Integer daireNo = null;
    private Integer kat = null;
    private Blok blok;
    private Kullanici kullanici;
    private DaireTipi daireTipi;
    private List<Long> selectedDaireList = new ArrayList<>();


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

    public DaireTipi getDaireTipi() {
        return daireTipi;
    }

    public void setDaireTipi(DaireTipi daireTipi) {
        this.daireTipi = daireTipi;
    }

    public List<Long> getSelectedDaireList() {
        return selectedDaireList;
    }

    public void setSelectedDaireList(List<Long> selectedDaireList) {
        this.selectedDaireList = selectedDaireList;
    }
}
