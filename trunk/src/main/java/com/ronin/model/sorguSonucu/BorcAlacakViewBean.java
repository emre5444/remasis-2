package com.ronin.model.sorguSonucu;

import com.ronin.model.Daire;
import com.ronin.model.constant.BorcTipi;

/**
 * Created by fcabi on 12.07.2014.
 */
public class BorcAlacakViewBean {

    BorcTipi borcTipi;
    Daire daire;
    Double tutar;
    Double rate;
    String aciklama;

    public BorcAlacakViewBean(BorcTipi borcTipi, Double tutar, Double rate) {
        this.borcTipi = borcTipi;
        this.tutar = tutar;
        this.rate = rate;
    }

    public BorcAlacakViewBean(Daire daire, BorcTipi borcTipi, Double tutar, Double rate) {
        this.daire = daire;
        this.borcTipi = borcTipi;
        this.tutar = tutar;
        this.rate = rate;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }

    public BorcTipi getBorcTipi() {
        return borcTipi;
    }

    public void setBorcTipi(BorcTipi borcTipi) {
        this.borcTipi = borcTipi;
    }

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }
}
