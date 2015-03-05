package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.TalepOnayDurumu;
import com.ronin.model.constant.TalepTipi;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "ariza_talebi")
public class ArizaTalebi extends Talep{

    public ArizaTalebi() {
    }

    @Column(name = "MUDAHELE_TARIHI")
    private Date mudahaleTarihi;

    @Column(name = "ARIZA_ACIKLAMA")
    private String arizaAciklama;

    public Date getMudahaleTarihi() {
        return mudahaleTarihi;
    }

    public void setMudahaleTarihi(Date mudahaleTarihi) {
        this.mudahaleTarihi = mudahaleTarihi;
    }

    public String getArizaAciklama() {
        return arizaAciklama;
    }

    public void setArizaAciklama(String arizaAciklama) {
        this.arizaAciklama = arizaAciklama;
    }
}
