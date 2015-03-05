/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import com.ronin.model.Sirket;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author msevim
 */
@Entity
@Table(name = "rol")
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findById", query = "SELECT r FROM Rol r WHERE r.id = :id"),
    @NamedQuery(name = "Rol.findByAd", query = "SELECT r FROM Rol r WHERE r.ad = :ad")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rol implements Serializable {


    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        Admin(1L,"Rol.admin"),//1
        User(2L,"Rol.user");//2

        private Long id;
        private String label;

        private ENUM(Long id,String label){
            this.id=id;
            this.label=label;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ad")
    private String ad;

    @JoinColumn(name = "yonetici_mi_id", referencedColumnName = "id")
    @ManyToOne
    private EvetHayir sistemYoneticisiMi;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "rol",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<KullaniciRol> kullaniciRolList;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "rol",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<RolYetki> rolYetkiList;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    public Rol() {
    }

    public Rol(Long id) {
        this.id = id;
    }

    public Rol(Long id, String ad) {
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

    @XmlTransient
    public List<KullaniciRol> getKullaniciRolList() {
        return kullaniciRolList;
    }

    public void setKullaniciRolList(List<KullaniciRol> kullaniciRolList) {
        this.kullaniciRolList = kullaniciRolList;
    }

    @XmlTransient
    public List<RolYetki> getRolYetkiList() {
        return rolYetkiList;
    }

    public void setRolYetkiList(List<RolYetki> rolYetkiList) {
        this.rolYetkiList = rolYetkiList;
    }

    public EvetHayir getSistemYoneticisiMi() {
        return sistemYoneticisiMi;
    }

    public void setSistemYoneticisiMi(EvetHayir sistemYoneticisiMi) {
        this.sistemYoneticisiMi = sistemYoneticisiMi;
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
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Rol[ id=" + id + " ]";
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}
