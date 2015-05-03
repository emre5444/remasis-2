/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.model.kriter;

import com.ronin.model.constant.Blok;

import java.io.Serializable;
import java.util.List;


public class HedefKitle implements Serializable {

    private List<Blok> blokList;

    public List<Blok> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<Blok> blokList) {
        this.blokList = blokList;
    }

}
