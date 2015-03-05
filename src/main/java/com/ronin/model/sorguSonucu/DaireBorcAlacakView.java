package com.ronin.model.sorguSonucu;

import com.ronin.model.Borc;
import com.ronin.model.Daire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcabi on 12.07.2014.
 */
public class DaireBorcAlacakView {

    Borc borc;
    Daire daire;
    List<DaireBorcKalemView> borcKalemViewList;

    public Borc getBorc() {
        return borc;
    }

    public void setBorc(Borc borc) {
        this.borc = borc;
    }

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }

    public List<DaireBorcKalemView> getBorcKalemViewList() {
        if(borcKalemViewList==null){
            borcKalemViewList = new ArrayList<>();
        }
        return borcKalemViewList;
    }

    public void setBorcKalemViewList(List<DaireBorcKalemView> borcKalemViewList) {
        this.borcKalemViewList = borcKalemViewList;
    }
}
