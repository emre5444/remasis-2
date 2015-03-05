package com.ronin.model;


import com.ronin.model.constant.BorcKalem;
import com.ronin.model.constant.Durum;

import javax.persistence.*;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "daire_borc_kalem")
public class DaireBorcKalem {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "daire_borc_id", referencedColumnName = "id")
    @ManyToOne
    private DaireBorc daireBorc;

    @JoinColumn(name = "borc_kalem_id", referencedColumnName = "id")
    @ManyToOne
    private BorcKalem borcKalem;

    @Column(name = "tutar")
    private Double tutar;

    @Column(name = "birim")
    private Double birim;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public DaireBorc getDaireBorc() {
        return daireBorc;
    }

    public void setDaireBorc(DaireBorc daireBorc) {
        this.daireBorc = daireBorc;
    }

    public BorcKalem getBorcKalem() {
        return borcKalem;
    }

    public void setBorcKalem(BorcKalem borcKalem) {
        this.borcKalem = borcKalem;
    }

    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Double getBirim() {
        return birim;
    }

    public void setBirim(Double birim) {
        this.birim = birim;
    }
}
