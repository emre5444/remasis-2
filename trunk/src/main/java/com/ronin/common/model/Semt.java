/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author esimsek
 */
@Entity
@Table(name = "semt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semt.findAll", query = "SELECT s FROM Semt s"),
    @NamedQuery(name = "Semt.findById", query = "SELECT s FROM Semt s WHERE s.id = :id"),
    @NamedQuery(name = "Semt.findByIlId", query = "SELECT s FROM Semt s WHERE s.ilId = :ilId"),
    @NamedQuery(name = "Semt.findByIlceId", query = "SELECT s FROM Semt s WHERE s.ilceId = :ilceId"),
    @NamedQuery(name = "Semt.findByAdBuyuk", query = "SELECT s FROM Semt s WHERE s.adBuyuk = :adBuyuk"),
    @NamedQuery(name = "Semt.findByAd", query = "SELECT s FROM Semt s WHERE s.ad = :ad"),
    @NamedQuery(name = "Semt.findByAdKucuk", query = "SELECT s FROM Semt s WHERE s.adKucuk = :adKucuk"),
    @NamedQuery(name = "Semt.findByPostaKodu", query = "SELECT s FROM Semt s WHERE s.postaKodu = :postaKodu")})
public class Semt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "il_id")
    private BigInteger ilId;
    @Column(name = "ilce_id")
    private BigInteger ilceId;
    @Column(name = "ad_buyuk")
    private String adBuyuk;
    @Column(name = "ad")
    private String ad;
    @Column(name = "ad_kucuk")
    private String adKucuk;
    @Column(name = "posta_kodu")
    private String postaKodu;

    public Semt() {
    }

    public Semt(Long id) {
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

    public BigInteger getIlceId() {
        return ilceId;
    }

    public void setIlceId(BigInteger ilceId) {
        this.ilceId = ilceId;
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

    public String getPostaKodu() {
        return postaKodu;
    }

    public void setPostaKodu(String postaKodu) {
        this.postaKodu = postaKodu;
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
        if (!(object instanceof Semt)) {
            return false;
        }
        Semt other = (Semt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Semt[ id=" + id + " ]";
    }
    
}
