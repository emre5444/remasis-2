package com.ronin.model.sorguSonucu;

import com.ronin.model.Daire;
import com.ronin.model.constant.BorcTipi;

/**
 * Created by fcabi on 12.07.2014.
 */
public class AnketSonucViewBean {

    Integer tutar;
    Double rate;
    String secim;

    public AnketSonucViewBean(Integer tutar, Double rate, String secim) {
        this.tutar = tutar;
        this.rate = rate;
        this.secim = secim;
    }

    public Integer getTutar() {
        return tutar;
    }

    public void setTutar(Integer tutar) {
        this.tutar = tutar;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getSecim() {
        return secim;
    }

    public void setSecim(String secim) {
        this.secim = secim;
    }
}
