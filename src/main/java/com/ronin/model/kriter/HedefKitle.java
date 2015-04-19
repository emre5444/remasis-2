/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.model.constant.Blok;

import java.io.Serializable;

/**
 * @author msevim
 */
public class HedefKitle implements Serializable {

    private Blok blok;

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}
