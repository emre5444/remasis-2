package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 08.03.2014.
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Durum.findAll", query = "SELECT d FROM Durum d")})
@Table(name = "durum")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Durum implements IAbstractEntity {

    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        Aktif(1L,"durum.aktif"),//1
        Pasif(2L,"durum.pasif"),//2
        Silinmis(3L,"durum.silinmis");//3

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

    public Durum(ENUM e) {
        this.id = e.getId();
    }

    public Durum() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;

    @Column(name = "aciklama")
    private String aciklama;

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

    public boolean isAktifMi(){
        return this.id.equals(ENUM.Aktif.getId());
    }

    public static Durum getAktifObject() {
        return new Durum(ENUM.Aktif);
    }

    public static Durum getPasifObject() {
        return new Durum(ENUM.Pasif);
    }

    public static Durum getSilinmisObject() {
        return new Durum(ENUM.Silinmis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Durum)) return false;

        Durum durum = (Durum) o;

        if (id != null ? !id.equals(durum.id) : durum.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
