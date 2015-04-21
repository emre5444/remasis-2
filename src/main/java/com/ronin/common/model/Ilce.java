/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esimsek
 */
@Entity
@Table(name = "ilce")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ilce.findAll", query = "SELECT i FROM Ilce i"),
    @NamedQuery(name = "Ilce.findById", query = "SELECT i FROM Ilce i WHERE i.id = :id"),
    @NamedQuery(name = "Ilce.findByIlId", query = "SELECT i FROM Ilce i WHERE i.ilId = :ilId"),
    @NamedQuery(name = "Ilce.findByAdBuyuk", query = "SELECT i FROM Ilce i WHERE i.adBuyuk = :adBuyuk"),
    @NamedQuery(name = "Ilce.findByAd", query = "SELECT i FROM Ilce i WHERE i.ad = :ad"),
    @NamedQuery(name = "Ilce.findByAdKucuk", query = "SELECT i FROM Ilce i WHERE i.adKucuk = :adKucuk")})
public class Ilce implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "il_id")
    private BigInteger ilId;
    @Column(name = "ad_buyuk")
    private String adBuyuk;
    @Column(name = "ad")
    private String ad;
    @Column(name = "ad_kucuk")
    private String adKucuk;

    public Ilce() {
    }

    public Ilce(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getIlId() {
        return ilId;
    }

    public void setIlId(BigInteger ilId) {
        this.ilId = ilId;
    }

    public String getAdBuyuk() {
        return adBuyuk;
    }

    public void setAdBuyuk(String adBuyuk) {
        this.adBuyuk = adBuyuk;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAdKucuk() {
        return adKucuk;
    }

    public void setAdKucuk(String adKucuk) {
        this.adKucuk = adKucuk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ilce)) {
            return false;
        }
        Ilce other = (Ilce) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Ilce[ id=" + id + " ]";
    }
    
}
