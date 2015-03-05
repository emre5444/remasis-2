/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "mahalle_koy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MahalleKoy.findAll", query = "SELECT m FROM MahalleKoy m"),
    @NamedQuery(name = "MahalleKoy.findById", query = "SELECT m FROM MahalleKoy m WHERE m.id = :id"),
    @NamedQuery(name = "MahalleKoy.findByIlId", query = "SELECT m FROM MahalleKoy m WHERE m.ilId = :ilId"),
    @NamedQuery(name = "MahalleKoy.findByIlceId", query = "SELECT m FROM MahalleKoy m WHERE m.ilceId = :ilceId"),
    @NamedQuery(name = "MahalleKoy.findBySemtId", query = "SELECT m FROM MahalleKoy m WHERE m.semtId = :semtId"),
    @NamedQuery(name = "MahalleKoy.findByAdBuyuk", query = "SELECT m FROM MahalleKoy m WHERE m.adBuyuk = :adBuyuk"),
    @NamedQuery(name = "MahalleKoy.findByAd", query = "SELECT m FROM MahalleKoy m WHERE m.ad = :ad"),
    @NamedQuery(name = "MahalleKoy.findByAdKucuk", query = "SELECT m FROM MahalleKoy m WHERE m.adKucuk = :adKucuk")})
public class MahalleKoy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "il_id")
    private BigInteger ilId;
    @Column(name = "ilce_id")
    private BigInteger ilceId;
    @Column(name = "semt_id")
    private BigInteger semtId;
    @Column(name = "ad_buyuk")
    private String adBuyuk;
    @Column(name = "ad")
    private String ad;
    @Column(name = "ad_kucuk")
    private String adKucuk;

    public MahalleKoy() {
    }

    public MahalleKoy(Long id) {
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

    public BigInteger getSemtId() {
        return semtId;
    }

    public void setSemtId(BigInteger semtId) {
        this.semtId = semtId;
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
        if (!(object instanceof MahalleKoy)) {
            return false;
        }
        MahalleKoy other = (MahalleKoy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.MahalleKoy[ id=" + id + " ]";
    }
    
}
