package com.ronin.model;

import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Kategori.findAll", query = "SELECT k FROM Kategori k")})
@Table(name = "kategori")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kategori implements IAbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 10)
    private Long id;

    @Column(name = "kisa_aciklama" ,length = 155)
    private String kisaAciklama;

    @Column(name = "aciklama" ,length = 555)
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

    public String getKisaAciklama() {
        return kisaAciklama;
    }

    public void setKisaAciklama(String kisaAciklama) {
        this.kisaAciklama = kisaAciklama;
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

    public String getMantiksalKisaAciklama(){
        if(kisaAciklama != null && kisaAciklama.length() > 80){
            return kisaAciklama.substring(0,80) + "...";
        }
        return kisaAciklama;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kategori kategori = (Kategori) o;

        if ((this.id == null && kategori.id != null) || (this.id != null && !this.id.equals(kategori.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
