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
@Table(name = "talep")
@Inheritance(strategy = InheritanceType.JOINED)
public class Talep {

    public Talep() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "TALEP_TIPI_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private TalepTipi talepTipi;

    @ManyToOne
    @JoinColumn(name = "KULLANICI_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Kullanici kullanici;


    @ManyToOne
    @JoinColumn(name = "TALEP_ONAY_DURUMU_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private TalepOnayDurumu talepOnayDurumu;

    @Column(name = "ISLEM_TARIHI")
    private Date islemTarihi;

    @Column(name = "ACIKLAMA")
    private String aciklama;

    @Column(name = "ONAY_ACIKLAMA")
    private String onayAciklama;

    @ManyToOne
    @JoinColumn(name = "ONAYLAYAN_KULLANICI_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Kullanici onaylayanKullanici;

    @Column(name = "ONAY_ZAMANI")
    private Date onayTarihi;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    public String getShortAciklama(){
        if (aciklama != null && aciklama.length() > 100) {
            return aciklama.substring(0, 100) + "...";
        }
        return aciklama;
    }

    public String getShortOnayAciklama() {
        if (onayAciklama != null && onayAciklama.length() > 100) {
            return onayAciklama.substring(0, 100) + "...";
        }
        return onayAciklama;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TalepTipi getTalepTipi() {
        return talepTipi;
    }

    public void setTalepTipi(TalepTipi talepTipi) {
        this.talepTipi = talepTipi;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public TalepOnayDurumu getTalepOnayDurumu() {
        return talepOnayDurumu;
    }

    public void setTalepOnayDurumu(TalepOnayDurumu talepOnayDurumu) {
        this.talepOnayDurumu = talepOnayDurumu;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getOnayAciklama() {
        return onayAciklama;
    }

    public void setOnayAciklama(String onayAciklama) {
        this.onayAciklama = onayAciklama;
    }

    public Kullanici getOnaylayanKullanici() {
        return onaylayanKullanici;
    }

    public void setOnaylayanKullanici(Kullanici onaylayanKullanici) {
        this.onaylayanKullanici = onaylayanKullanici;
    }

    public Date getOnayTarihi() {
        return onayTarihi;
    }

    public void setOnayTarihi(Date onayTarihi) {
        this.onayTarihi = onayTarihi;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}
