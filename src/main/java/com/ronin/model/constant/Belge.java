package com.ronin.model.constant;

import com.ronin.common.model.Kullanici;
import com.ronin.model.Daire;
import com.ronin.model.Sirket;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "belge")
public class Belge {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name="content")
    @Lob
    private Blob content;

    @Column(name = "islem_tarihi")
    private Date islemTarihi;

    @Column(name = "data_name")
    private String dataName;

    @Column(name = "size")
    private Long size;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @JoinColumn(name = "belge_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private BelgeTipi belgeTipi;

    @Column(name = "aciklama")
    private String aciklama;

    @JoinColumn(name = "sirket_id", referencedColumnName = "id")
    @ManyToOne
    private Sirket sirket;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public BelgeTipi getBelgeTipi() {
        return belgeTipi;
    }

    public void setBelgeTipi(BelgeTipi belgeTipi) {
        this.belgeTipi = belgeTipi;
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
}
