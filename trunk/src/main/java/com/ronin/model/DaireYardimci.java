package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;

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
@Table(name = "daire_yardimci")
public class DaireYardimci {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "daire_id", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "ad")
    private String ad;

    @Column(name = "soyad")
    private String soyad;

    @Column(name = "gorev")
    private String gorev;

    @Column(name = "gunluk_calisma_suresi")
    private Long gunlukCalismaSuresi;

    @Column(name = "gorev_baslama_tarihi")
    private Date gorevBaslamaTarihi;

    @Column(name = "gorev_bitis_tarihi")
    private Date gorevBitisTarihi;

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

    public Date getGorevBitisTarihi() {
        return gorevBitisTarihi;
    }

    public void setGorevBitisTarihi(Date gorevBitisTarihi) {
        this.gorevBitisTarihi = gorevBitisTarihi;
    }

    public Date getGorevBaslamaTarihi() {
        return gorevBaslamaTarihi;
    }

    public void setGorevBaslamaTarihi(Date gorevBaslamaTarihi) {
        this.gorevBaslamaTarihi = gorevBaslamaTarihi;
    }

    public Long getGunlukCalismaSuresi() {
        return gunlukCalismaSuresi;
    }

    public void setGunlukCalismaSuresi(Long gunlukCalismaSuresi) {
        this.gunlukCalismaSuresi = gunlukCalismaSuresi;
    }

    public String getGorev() {
        return gorev;
    }

    public void setGorev(String gorev) {
        this.gorev = gorev;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}
