package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mail_sender")
public class MailSender {

    public MailSender() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "mesaj")
    private String mesaj;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "islem_tarihi")
    private Date islemTarihi;

    @JoinColumn(name = "bildirim_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private BildirimTipi bildirimTipi;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public BildirimTipi getBildirimTipi() {
        return bildirimTipi;
    }

    public void setBildirimTipi(BildirimTipi bildirimTipi) {
        this.bildirimTipi = bildirimTipi;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}