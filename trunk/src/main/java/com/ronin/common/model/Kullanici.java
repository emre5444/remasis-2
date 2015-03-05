/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.Sirket;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author msevim
 */
@Entity
@Table(name = "kullanici")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kullanici.findAll", query = "SELECT k FROM Kullanici k"),
    @NamedQuery(name = "Kullanici.findById", query = "SELECT k FROM Kullanici k WHERE k.id = :id"),
    @NamedQuery(name = "Kullanici.findByAd", query = "SELECT k FROM Kullanici k WHERE k.ad = :ad"),
    @NamedQuery(name = "Kullanici.findBySoyad", query = "SELECT k FROM Kullanici k WHERE k.soyad = :soyad"),
    @NamedQuery(name = "Kullanici.findByTckimlik", query = "SELECT k FROM Kullanici k WHERE k.tckimlik = :tckimlik"),
    @NamedQuery(name = "Kullanici.findByUsername", query = "SELECT k FROM Kullanici k WHERE k.username = :username"),
    @NamedQuery(name = "Kullanici.findByEmail", query = "SELECT k FROM Kullanici k WHERE k.email = :email"),
    @NamedQuery(name = "Kullanici.findByGsm", query = "SELECT k FROM Kullanici k WHERE k.gsm = :gsm")})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kullanici implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "ad")

    private String ad;

    @Column(name = "soyad")
    private String soyad;

    @Column(name = "tckimlik")
    private String tckimlik;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "gsm")
    private String gsm;

    @Column(name = "adres")
    private String adres;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "kullanici",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<KullaniciRol> kullaniciRolList;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "kullanici",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<KullaniciDaire> kullaniciDaireList;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "kullanici",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<KullaniciSirket> kullaniciSirketList;
    
    @Version
    private Long versiyon;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;


    public String getAdSoyad(){
        return this.getAd() == null ? this.getId().toString() : (this.ad + " " + this.soyad);
    }

    public Kullanici() {
    }

    public Kullanici(Long id) {
        this.id = id;
    }

    public Kullanici(Long id, String ad, String soyad, String username, String password) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.username = username;
        this.password = password;
    }

    public List<Sirket> getSirketList(){
        List<Sirket> sirketList = new ArrayList<>();
         for(KullaniciSirket ks : kullaniciSirketList){
             sirketList.add(ks.getSirket());
         }
        return sirketList;
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

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTckimlik() {
        return tckimlik;
    }

    public void setTckimlik(String tckimlik) {
        this.tckimlik = tckimlik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Set<KullaniciRol> getKullaniciRolList() {
        return kullaniciRolList;
    }

    public void setKullaniciRolList(Set<KullaniciRol> kullaniciRolList) {
        this.kullaniciRolList = kullaniciRolList;
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
        if (!(object instanceof Kullanici)) {
            return false;
        }
        Kullanici other = (Kullanici) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getVersiyon() {
        return versiyon;
    }

    public void setVersiyon(Long versiyon) {
        this.versiyon = versiyon;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public List<KullaniciDaire> getKullaniciDaireList() {
        return kullaniciDaireList;
    }

    public void setKullaniciDaireList(List<KullaniciDaire> kullaniciDaireList) {
        this.kullaniciDaireList = kullaniciDaireList;
    }

    public List<KullaniciSirket> getKullaniciSirketList() {
        return kullaniciSirketList;
    }

    public void setKullaniciSirketList(List<KullaniciSirket> kullaniciSirketList) {
        this.kullaniciSirketList = kullaniciSirketList;
    }

    @Override
    public String toString() {
        return "com.ronin.model.Kullanici[ id=" + id + " ]";
    }
    
}
