package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "envanter")
public class Envanter {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 10)
    private Long id;

    @Column(name = "barkod_no",length = 155)
    private String barkodNo;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @JoinColumn(name = "kategori_id", referencedColumnName = "id")
    @ManyToOne
    private Kategori kategori;

    @Column(name = "marka",length = 155)
    private String marka;

    @Column(name = "model",length = 155)
    private String model;

    @Column(name = "urun_adi",length = 155)
    private String urunAdi;

    @Column(name = "satici_firma",length = 256)
    private String saticiFirma;

    @Column(name = "alimi_yapan_personel",length = 155)
    private String alimiYapanPersonel;

    @Column(name = "zimmetli_personel",length = 155)
    private String zimmetliPersonel;

    @Column(name = "departman",length = 155)
    private String departman;

    @Column(name = "alim_zamani",length = 20)
    private Date alimTarihi;

    @Column(name = "garanti_baslangic_zamani",length = 20)
    private Date garantiBaslangicTarihi;

    @Column(name = "garanti_suresi",length = 3)
    private Long garantiSuresi;

    @Column(name = "miktar",length = 10)
    private Long miktar;

    @Column(name = "birim_fiyat",length = 10)
    private Double birimFiyat;

    @Column(name = "aciklama",length = 2056)
    private String aciklama;

    @Column(name = "tanitim_zamani",length = 20)
    private Date tanitimZamani;

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

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getMantiksalAciklama(){
        if(aciklama != null && aciklama.length() > 100){
          return aciklama.substring(0,100) + "...";
        }
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
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

    public Double getBirimFiyat() {
        return birimFiyat;
    }

    public void setBirimFiyat(Double birimFiyat) {
        this.birimFiyat = birimFiyat;
    }

    public Long getMiktar() {
        return miktar;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public Long getGarantiSuresi() {
        return garantiSuresi;
    }

    public void setGarantiSuresi(Long garantiSuresi) {
        this.garantiSuresi = garantiSuresi;
    }

    public Date getGarantiBaslangicTarihi() {
        return garantiBaslangicTarihi;
    }

    public void setGarantiBaslangicTarihi(Date garantiBaslangicTarihi) {
        this.garantiBaslangicTarihi = garantiBaslangicTarihi;
    }

    public Date getAlimTarihi() {
        return alimTarihi;
    }

    public void setAlimTarihi(Date alimTarihi) {
        this.alimTarihi = alimTarihi;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getZimmetliPersonel() {
        return zimmetliPersonel;
    }

    public void setZimmetliPersonel(String zimmetliPersonel) {
        this.zimmetliPersonel = zimmetliPersonel;
    }

    public String getAlimiYapanPersonel() {
        return alimiYapanPersonel;
    }

    public void setAlimiYapanPersonel(String alimiYapanPersonel) {
        this.alimiYapanPersonel = alimiYapanPersonel;
    }

    public String getSaticiFirma() {
        return saticiFirma;
    }

    public void setSaticiFirma(String saticiFirma) {
        this.saticiFirma = saticiFirma;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getBarkodNo() {
        return barkodNo;
    }

    public void setBarkodNo(String barkodNo) {
        this.barkodNo = barkodNo;
    }
}
