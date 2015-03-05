package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bilgilendirme_log")
public class BilgilendirmeLog {

    public BilgilendirmeLog() {
    }

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

    @Column(name = "mesaj")
    private String mesaj;

    @Column(name = "email")
    private String email;

    @Column(name = "bilgilendirme_donem")
    private Date bilgilendirmeDonem;

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

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBilgilendirmeDonem() {
        return bilgilendirmeDonem;
    }

    public void setBilgilendirmeDonem(Date bilgilendirmeDonem) {
        this.bilgilendirmeDonem = bilgilendirmeDonem;
    }
}