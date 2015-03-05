package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "KULLANICI_ID", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @JoinColumn(name = "DAIRE_ID", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;

    @Column(name = "NOTIFICATION", length = 1024)
    private String notification;

    @Column(name = "ACIKLAMA", length = 255)
    private String aciklama;

    @Column(name = "TANIMLANMA_ZAMANI")
    private Date tanimlanmaZamani;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    @JoinColumn(name = "DURUM_ID", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @JoinColumn(name = "BILDIRIM_TIPI_ID", referencedColumnName = "id")
    @ManyToOne
    private BildirimTipi bildirimTipi;

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

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getTanimlanmaZamani() {
        return tanimlanmaZamani;
    }

    public void setTanimlanmaZamani(Date tanimlanmaZamani) {
        this.tanimlanmaZamani = tanimlanmaZamani;
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

    public BildirimTipi getBildirimTipi() {
        return bildirimTipi;
    }

    public void setBildirimTipi(BildirimTipi bildirimTipi) {
        this.bildirimTipi = bildirimTipi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification natification = (Notification) o;

        if (!id.equals(natification.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isAktifMi() {
        return this.getDurum().isAktifMi();
    }
}
