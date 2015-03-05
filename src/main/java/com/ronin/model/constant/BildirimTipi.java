package com.ronin.model.constant;

import com.ronin.common.model.KullaniciRol;
import com.ronin.model.BildirimTipiRol;
import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by fcabi on 09.03.2014.
 */

@Entity
@Table(name = "bildirim_tipi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BildirimTipi.findAll", query = "SELECT b FROM BildirimTipi b")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BildirimTipi implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        AIDAT(1L,"durum.aktif"),
        DUYURU(2L,"durum.aktif"),
        ARIZA(3L,"durum.aktif"),
        AIDAT_ITIRAZ(4L,"durum.aktif"),
        ILAN_EKLEME(5L,"durum.aktif"),
        SIKAYET(6L,"durum.aktif"),
        BELGE_TALEBI(7L,"durum.aktif"),
        ANKET(8L,"durum.aktif");
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

    public BildirimTipi(ENUM e) {
        this.id = e.getId();
    }

    public BildirimTipi() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;
    @Column(name = "aciklama")
    private String aciklama;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "bildirimTipi",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<BildirimTipiRol> bildirimTipiRolList;

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

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public boolean isAidatMi(){
        return this.getId().equals(ENUM.AIDAT.getId());
    }

    public boolean isAidatItirazMi(){
        return this.getId().equals(ENUM.AIDAT_ITIRAZ.getId());
    }

    public boolean isArizaMi(){
        return this.getId().equals(ENUM.ARIZA.getId());
    }

    public boolean isDuyuruMu(){
        return this.getId().equals(ENUM.DUYURU.getId());
    }

    public boolean isIlanEklemeMi(){
        return this.getId().equals(ENUM.ILAN_EKLEME.getId());
    }

    public boolean isSikayetMi(){
        return this.getId().equals(ENUM.SIKAYET.getId());
    }

    public boolean isBelgeTalepMi(){
        return this.getId().equals(ENUM.BELGE_TALEBI.getId());
    }

    public boolean isAnketMi(){
        return this.getId().equals(ENUM.ANKET.getId());
    }

    public List<BildirimTipiRol> getBildirimTipiRolList() {
        return bildirimTipiRolList;
    }

    public void setBildirimTipiRolList(List<BildirimTipiRol> bildirimTipiRolList) {
        this.bildirimTipiRolList = bildirimTipiRolList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BildirimTipi)) return false;

        BildirimTipi bildirimTipi = (BildirimTipi) o;

        if (id != null ? !id.equals(bildirimTipi.id) : bildirimTipi.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
