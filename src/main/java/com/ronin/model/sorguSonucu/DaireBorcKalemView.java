package com.ronin.model.sorguSonucu;

import com.ronin.model.constant.BorcKalem;

/**
 * Created by fcabi on 12.07.2014.
 */
public class DaireBorcKalemView {

    BorcKalem borcKalem;
    Double tutar;

    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }

    public BorcKalem getBorcKalem() {
        return borcKalem;
    }

    public void setBorcKalem(BorcKalem borcKalem) {
        this.borcKalem = borcKalem;
    }
}
