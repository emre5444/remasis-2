package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sifre_hatirlatma")
public class SifreHatirlatma {

    public SifreHatirlatma() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "islem_tarihi")
    private Timestamp islemTarihi;


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

    public Timestamp getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Timestamp islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

}