/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author msevim
 */
@Entity
@Table(name = "yetki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Yetki.findAll", query = "SELECT y FROM Yetki y"),
    @NamedQuery(name = "Yetki.findById", query = "SELECT y FROM Yetki y WHERE y.id = :id"),
    @NamedQuery(name = "Yetki.findByAd", query = "SELECT y FROM Yetki y WHERE y.ad = :ad"),
    @NamedQuery(name = "Yetki.findByDurumId", query = "SELECT y FROM Yetki y WHERE y.durum.id = :durumId")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Yetki implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ad")
    private String ad;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @Basic(optional = false)
    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "BAGLI_OLDUGU_YETKI_ID")
    @Fetch(FetchMode.SELECT)
    private Yetki bagliOlduguYetki;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yetki", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<RolYetki> rolYetkiList;

    public Yetki() {
    }

    public Yetki(Long id) {
        this.id = id;
    }

    public Yetki(Long id, String ad) {
        this.id = id;
        this.ad = ad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlTransient
    public List<RolYetki> getRolYetkiList() {
        return rolYetkiList;
    }

    public void setRolYetkiList(List<RolYetki> rolYetkiList) {
        this.rolYetkiList = rolYetkiList;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public Yetki getBagliOlduguYetki() {
        return bagliOlduguYetki;
    }

    public void setBagliOlduguYetki(Yetki bagliOlduguYetki) {
        this.bagliOlduguYetki = bagliOlduguYetki;
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
        if (!(object instanceof Yetki)) {
            return false;
        }
        Yetki other = (Yetki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Yetki[ id=" + id + " ]";
    }
    
}
