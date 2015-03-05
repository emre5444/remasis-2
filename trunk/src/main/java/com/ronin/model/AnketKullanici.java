package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.EvetHayir;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "anket_kullanici")
public class AnketKullanici {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @JoinColumn(name = "anket_id", referencedColumnName = "id")
    @ManyToOne
    private Anket anket;

    @JoinColumn(name = "anket_secim_id", referencedColumnName = "id")
    @ManyToOne
    private AnketSecim anketSecim;

    @Column(name = "tanitim_zamani")
    private Date tanitimZamani;

    @Column(name = "ACIKLAMA")
    private String aciklama;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Anket getAnket() {
        return anket;
    }

    public void setAnket(Anket anket) {
        this.anket = anket;
    }

    public AnketSecim getAnketSecim() {
        return anketSecim;
    }

    public void setAnketSecim(AnketSecim anketSecim) {
        this.anketSecim = anketSecim;
    }

    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
