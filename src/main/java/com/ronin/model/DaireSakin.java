package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "daire_sakin")
public class DaireSakin {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "bagli_kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici bagliKullanici;

    @JoinColumn(name = "daire_id", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "tanitim_zamani")
    private Date tanitimZamani;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getBagliKullanici() {
        return bagliKullanici;
    }

    public void setBagliKullanici(Kullanici bagliKullanici) {
        this.bagliKullanici = bagliKullanici;
    }

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }
}
