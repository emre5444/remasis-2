package com.ronin.model;

import com.ronin.model.constant.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "borc")
public class Borc {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "dekont_no")
    private String dekontNo;

    @Column(name = "islem_tarihi")
    private Date islemTarihi;

    @Column(name = "odeme_vadesi")
    private Date odemeVadesi;

    @Column(name = "son_odeme_tarihi")
    private Date sonOdemeTarihi;


    @Column(name = "borc")
    private Double borc;


    @Column(name = "aciklama")
    private String aciklama;


    @JoinColumn(name = "odeme_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private OdemeTipi odemeTipi;

    @JoinColumn(name = "banka_id", referencedColumnName = "id")
    @ManyToOne
    private Banka banka;

    @Column(name = "odenen_tutar")
    private Double odenenTutar;

    @Transient
    private Double bakiye;

    @Transient
    public boolean islendiMi;

    @Column(name = "odeme_aciklama")
    private String odemeAciklama;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @Column(name = "eslestirme_id")
    private Long eslestirmeId;

    @JoinColumn(name = "islem_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private KaynakTipi islemTipi;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    @Column(name = "IADE_MI")
    private Long iadeMi;

    public Borc() {

    }

    public Borc(Borc borc) {
        this.aciklama = borc.getAciklama();
        this.borc = borc.getBorc();
        this.islemTarihi =borc.getIslemTarihi();
        this.sonOdemeTarihi = borc.getSonOdemeTarihi();
        this.odenenTutar =borc.getOdenenTutar();
        this.odemeAciklama =borc.getOdemeAciklama();
        this.odemeVadesi =borc.getOdemeVadesi();
    }

    public boolean isBorcluMu(){
        return bakiye < 0 ? true : false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDekontNo() {
        return dekontNo;
    }

    public void setDekontNo(String dekontNo) {
        this.dekontNo = dekontNo;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public Date getOdemeVadesi() {
        return odemeVadesi;
    }

    public void setOdemeVadesi(Date odemeVadesi) {
        this.odemeVadesi = odemeVadesi;
    }

    public Date getSonOdemeTarihi() {
        return sonOdemeTarihi;
    }

    public void setSonOdemeTarihi(Date sonOdemeTarihi) {
        this.sonOdemeTarihi = sonOdemeTarihi;
    }

    public Double getBorc() {
        return borc;
    }

    public void setBorc(Double borc) {
        this.borc = borc;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public OdemeTipi getOdemeTipi() {
        return odemeTipi;
    }

    public void setOdemeTipi(OdemeTipi odemeTipi) {
        this.odemeTipi = odemeTipi;
    }

    public Banka getBanka() {
        return banka;
    }

    public void setBanka(Banka banka) {
        this.banka = banka;
    }

    public Double getOdenenTutar() {
        return odenenTutar;
    }

    public void setOdenenTutar(Double odenenTutar) {
        this.odenenTutar = odenenTutar;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public String getOdemeAciklama() {
        return odemeAciklama;
    }

    public void setOdemeAciklama(String odemeAciklama) {
        this.odemeAciklama = odemeAciklama;
    }

    public Double getBakiye() {
        return bakiye;
    }

    public void setBakiye(Double bakiye) {
        this.bakiye = bakiye;
    }

    public KaynakTipi getIslemTipi() {
        return islemTipi;
    }

    public void setIslemTipi(KaynakTipi islemTipi) {
        this.islemTipi = islemTipi;
    }

    public Long getEslestirmeId() {
        return eslestirmeId;
    }

    public void setEslestirmeId(Long eslestirmeId) {
        this.eslestirmeId = eslestirmeId;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }

    public Long getIadeMi() {
        return iadeMi;
    }

    public void setIadeMi(Long iadeMi) {
        this.iadeMi = iadeMi;
    }
}
