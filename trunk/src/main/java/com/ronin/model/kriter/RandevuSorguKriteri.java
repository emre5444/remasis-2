/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.model.constant.RandevuTipi;

import java.io.Serializable;

/**
 * @author fcabi
 */
public class RandevuSorguKriteri implements Serializable {

    private RandevuTipi randevuTipi;

    public RandevuTipi getRandevuTipi() {
        return randevuTipi;
    }

    public void setRandevuTipi(RandevuTipi randevuTipi) {
        this.randevuTipi = randevuTipi;
    }
}
