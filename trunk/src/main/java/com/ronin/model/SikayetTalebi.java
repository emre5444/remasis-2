package com.ronin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "sikayet_talebi")
public class SikayetTalebi extends Talep{

    public SikayetTalebi() {
    }

    @Column(name = "SIKAYET_ACIKLAMA")
    private String sikayetAciklama;

    public String getSikayetAciklama() {
        return sikayetAciklama;
    }

    public void setSikayetAciklama(String sikayetAciklama) {
        this.sikayetAciklama = sikayetAciklama;
    }
}
