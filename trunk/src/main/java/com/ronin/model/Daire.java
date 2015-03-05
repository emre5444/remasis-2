package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.BorcTipi;
import com.ronin.model.constant.DaireTipi;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "daire")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Daire {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "daire_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private DaireTipi daireTipi;

    @Column(name = "alan")
    private Double alan;

    @JoinColumn(name = "blok_id", referencedColumnName = "id")
    @ManyToOne
    private Blok blok;

    @Column(name = "kat")
    private Integer kat;

    @Column(name = "daire_no")
    private Integer daireNo;

    @Column(name = "daire_kodu")
    private String daireKodu;

    @Column(name = "bakiye")
    private Double guncelBakiye;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @JoinColumn(name = "borc_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private BorcTipi borcTipi;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "daire", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<KullaniciDaire> kullaniciDaireList;

    public List<Kullanici> getKullaniciSakimList() {
        List<Kullanici> kullaniciList = new ArrayList<>();
        if (kullaniciDaireList.size() > 1) {
            for (KullaniciDaire kd : kullaniciDaireList) {
                if (!kd.getKullaniciTipi().isEvsahibiMi()) {
                    kullaniciList.add(kd.getKullanici());
                }
            }
        } else {
            for (KullaniciDaire kd : kullaniciDaireList) {
                kullaniciList.add(kd.getKullanici());
            }
        }
        return kullaniciList;
    }

    public Kullanici getKullaniciMalik() {
        for (KullaniciDaire kd : kullaniciDaireList) {
            if (kd.getKullaniciTipi().isEvsahibiMi()) {
                return kd.getKullanici();
            }
        }
        return null;
    }

    public String getDaireBlokAd() {
        return this.blok.getAciklama() + " " + this.daireNo.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DaireTipi getDaireTipi() {
        return daireTipi;
    }

    public void setDaireTipi(DaireTipi daireTipi) {
        this.daireTipi = daireTipi;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Integer getDaireNo() {
        return daireNo;
    }

    public void setDaireNo(Integer daireNo) {
        this.daireNo = daireNo;
    }

    public Integer getKat() {
        return kat;
    }

    public void setKat(Integer kat) {
        this.kat = kat;
    }

    public Double getAlan() {
        return alan;
    }

    public void setAlan(Double alan) {
        this.alan = alan;
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }

    public String getDaireKodu() {
        return daireKodu;
    }

    public void setDaireKodu(String daireKodu) {
        this.daireKodu = daireKodu;
    }

    public Double getGuncelBakiye() {
        return guncelBakiye;
    }

    public void setGuncelBakiye(Double guncelBakiye) {
        this.guncelBakiye = guncelBakiye;
    }

    public BorcTipi getBorcTipi() {
        return borcTipi;
    }

    public void setBorcTipi(BorcTipi borcTipi) {
        this.borcTipi = borcTipi;
    }

    @XmlTransient
    public List<KullaniciDaire> getKullaniciDaireList() {
        return kullaniciDaireList;
    }

    public void setKullaniciDaireList(List<KullaniciDaire> kullaniciDaireList) {
        this.kullaniciDaireList = kullaniciDaireList;
    }
}