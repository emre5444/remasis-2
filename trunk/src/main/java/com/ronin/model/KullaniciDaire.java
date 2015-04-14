package com.ronin.model;


import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.constant.KullaniciTipi;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

/**
 * Created by ealtun on 09.03.2014.
 */

@Entity
@Table(name = "kullanici_daire")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KullaniciDaire {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @JoinColumn(name = "daire_id", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;


    @JoinColumn(name = "kallanici_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private KullaniciTipi kullaniciTipi;

    @JoinColumn(name = "varsayilan_mi", referencedColumnName = "id")
    @ManyToOne
    private EvetHayir varsayilanMi;

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

    public KullaniciTipi getKullaniciTipi() {
        return kullaniciTipi;
    }

    public void setKullaniciTipi(KullaniciTipi kullaniciTipi) {
        this.kullaniciTipi = kullaniciTipi;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public EvetHayir getVarsayilanMi() {
        return varsayilanMi;
    }

    public void setVarsayilanMi(EvetHayir varsayilanMi) {
        this.varsayilanMi = varsayilanMi;
    }
}
