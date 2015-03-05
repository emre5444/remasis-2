/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esimsek
 */
@Entity
@Table(name = "il")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Il.findAll", query = "SELECT i FROM Il i"),
    @NamedQuery(name = "Il.findById", query = "SELECT i FROM Il i WHERE i.id = :id"),
    @NamedQuery(name = "Il.findByPlaka", query = "SELECT i FROM Il i WHERE i.plaka = :plaka"),
    @NamedQuery(name = "Il.findByAdBuyuk", query = "SELECT i FROM Il i WHERE i.adBuyuk = :adBuyuk"),
    @NamedQuery(name = "Il.findByAd", query = "SELECT i FROM Il i WHERE i.ad = :ad"),
    @NamedQuery(name = "Il.findByAdKucuk", query = "SELECT i FROM Il i WHERE i.adKucuk = :adKucuk")})
public class Il implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "plaka")
    private String plaka;
    @Column(name = "ad_buyuk")
    private String adBuyuk;
    @Column(name = "ad")
    private String ad;
    @Column(name = "ad_kucuk")
    private String adKucuk;
    

    public Il() {
    }

    public Il(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
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
        if (!(object instanceof Il)) {
            return false;
        }
        Il other = (Il) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Il[ id=" + id + " ]";
    }
    
}
