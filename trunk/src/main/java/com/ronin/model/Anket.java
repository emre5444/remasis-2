package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "anket")
public class Anket {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "anket_konusu")
    private String anketKonusu;

    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "tanitim_zamani")
    private Date tanitimZamani;

    @JoinColumn(name = "aktif_mi", referencedColumnName = "id")
    @ManyToOne
    private EvetHayir aktifMi;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "anket", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnketKullanici> anketKullaniciList;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "anket", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnketSecim> anketSecimList;

    @Transient
    private String yeniSecenek;

    @Transient
    private AnketSecim selectedAnketSecim;

    @Transient
    private EvetHayir anketeKatinildiMi;

    @Transient
    private String oyAciklama;


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

    public String getAnketKonusu() {
        return anketKonusu;
    }

    public void setAnketKonusu(String anketKonusu) {
        this.anketKonusu = anketKonusu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
    }

    public EvetHayir getAktifMi() {
        return aktifMi;
    }

    public void setAktifMi(EvetHayir aktifMi) {
        this.aktifMi = aktifMi;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public String getYeniSecenek() {
        return yeniSecenek;
    }

    public void setYeniSecenek(String yeniSecenek) {
        this.yeniSecenek = yeniSecenek;
    }

    public List<AnketSecim> getAnketSecimList() {
        return anketSecimList;
    }

    public void setAnketSecimList(List<AnketSecim> anketSecimList) {
        this.anketSecimList = anketSecimList;
    }

    public List<AnketKullanici> getAnketKullaniciList() {
        return anketKullaniciList;
    }

    public void setAnketKullaniciList(List<AnketKullanici> anketKullaniciList) {
        this.anketKullaniciList = anketKullaniciList;
    }

    public AnketSecim getSelectedAnketSecim() {
        return selectedAnketSecim;
    }

    public void setSelectedAnketSecim(AnketSecim selectedAnketSecim) {
        this.selectedAnketSecim = selectedAnketSecim;
    }

    public String getOyAciklama() {
        return oyAciklama;
    }

    public void setOyAciklama(String oyAciklama) {
        this.oyAciklama = oyAciklama;
    }

    public EvetHayir getAnketeKatinildiMi() {
        return anketeKatinildiMi;
    }

    public void setAnketeKatinildiMi(EvetHayir anketeKatinildiMi) {
        this.anketeKatinildiMi = anketeKatinildiMi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Anket)) return false;

        Anket anket = (Anket) o;

        if (!id.equals(anket.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
