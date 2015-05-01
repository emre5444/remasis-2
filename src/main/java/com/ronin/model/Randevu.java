package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.OnayDurumu;
import com.ronin.model.constant.RandevuTipi;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "randevu")
public class Randevu {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 6)
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "baslangic_zamani")
    private Date baslangicZamani;

    @Column(name = "bitis_zamani")
    private Date bitisZamani;

    @JoinColumn(name = "randevu_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private RandevuTipi randevuTipi;

    @JoinColumn(name = "onay_durumu_id", referencedColumnName = "id")
    @ManyToOne
    private OnayDurumu onayDurumu;

    @Column(name = "onaylayan_kullanici_id", length = 5)
    private Long onaylayanKullaniciId;

    @Column(name = "onay_zamani")
    private Date onayZamani;

    @Column(name = "aciklama", length = 1024)
    private String aciklama;

    @ManyToOne
    @JoinColumn(name = "sirket_id", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    @Column(name = "iptal_kullanici_id", length = 5)
    private Long iptalEdenKullaniciId;

    @Column(name = "iptal_zamani")
    private Date iptalZamani;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

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

    public Date getBaslangicZamani() {
        return baslangicZamani;
    }

    public void setBaslangicZamani(Date baslangicZamani) {
        this.baslangicZamani = baslangicZamani;
    }

    public Date getBitisZamani() {
        return bitisZamani;
    }

    public void setBitisZamani(Date bitisZamani) {
        this.bitisZamani = bitisZamani;
    }

    public RandevuTipi getRandevuTipi() {
        return randevuTipi;
    }

    public void setRandevuTipi(RandevuTipi randevuTipi) {
        this.randevuTipi = randevuTipi;
    }

    public OnayDurumu getOnayDurumu() {
        return onayDurumu;
    }

    public void setOnayDurumu(OnayDurumu onayDurumu) {
        this.onayDurumu = onayDurumu;
    }

    public Long getOnaylayanKullaniciId() {
        return onaylayanKullaniciId;
    }

    public void setOnaylayanKullaniciId(Long onaylayanKullaniciId) {
        this.onaylayanKullaniciId = onaylayanKullaniciId;
    }

    public Date getOnayZamani() {
        return onayZamani;
    }

    public void setOnayZamani(Date onayZamani) {
        this.onayZamani = onayZamani;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }

    public Long getIptalEdenKullaniciId() {
        return iptalEdenKullaniciId;
    }

    public void setIptalEdenKullaniciId(Long iptalEdenKullaniciId) {
        this.iptalEdenKullaniciId = iptalEdenKullaniciId;
    }

    public Date getIptalZamani() {
        return iptalZamani;
    }

    public void setIptalZamani(Date iptalZamani) {
        this.iptalZamani = iptalZamani;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public boolean isOnayBekliyor(){
        return (onayDurumu!=null && onayDurumu.equals(OnayDurumu.getOnayBekliyorObject()));
    }

    public boolean isOnaylandi(){
        return (onayDurumu!=null && onayDurumu.equals(OnayDurumu.getOnaylandiObject()));
    }

    public boolean isOnaylanmadi(){
        return (onayDurumu!=null && onayDurumu.equals(OnayDurumu.getOnaylanmadiObject()));
    }
}
