package com.ronin.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sifre_hatirlatma_log")
public class SifreHatirlatmaLog {

    public SifreHatirlatmaLog() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kullanici_id")
    private Long kullaniciId;

    @Column(name = "email")
    private String email;

    @Column(name = "sifre")
    private String sifre;

    @Column(name = "hash_sifre")
    private String hashSifre;

    @Column(name = "islem_tarihi")
    private Timestamp islemTarihi;

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

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getHashSifre() {
        return hashSifre;
    }

    public void setHashSifre(String hashSifre) {
        this.hashSifre = hashSifre;
    }

    public Timestamp getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Timestamp islemTarihi) {
        this.islemTarihi = islemTarihi;
    }
}