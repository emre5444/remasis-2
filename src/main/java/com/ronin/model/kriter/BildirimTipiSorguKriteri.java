/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.model.constant.BildirimTipi;

import java.io.Serializable;

/**
 *
 * @author msevim
 */
public class BildirimTipiSorguKriteri implements Serializable{
    
    BildirimTipi bildirimTipi;

    public BildirimTipi getBildirimTipi() {
        return bildirimTipi;
    }

    public void setBildirimTipi(BildirimTipi bildirimTipi) {
        this.bildirimTipi = bildirimTipi;
    }
}
