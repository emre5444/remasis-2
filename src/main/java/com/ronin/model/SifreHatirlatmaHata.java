package com.ronin.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sifre_hatirlatma_hata")
public class SifreHatirlatmaHata {

    public SifreHatirlatmaHata() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kullanici_id")
    private Long kullaniciId;

    @Column(name = "email")
    private String email;

    @Column(name = "hata_detayi")
    private String hataDetayi;

    @Column(name = "hata_zamani")
    private Timestamp hataZamani;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(Long kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHataDetayi() {
        return hataDetayi;
    }

    public void setHataDetayi(String hataDetayi) {
        this.hataDetayi = hataDetayi;
    }

    public Timestamp getHataZamani() {
        return hataZamani;
    }

    public void setHataZamani(Timestamp hataZamani) {
        this.hataZamani = hataZamani;
    }
}