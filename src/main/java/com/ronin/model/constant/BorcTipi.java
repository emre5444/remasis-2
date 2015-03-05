package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 09.03.2014.
 */

@Entity
@Table(name = "borc_tipi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BorcTipi.findAll", query = "SELECT r FROM BorcTipi r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BorcTipi implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        BORC(1L, "durum.aktif"),//1
        ALACAK(2L, "durum.pasif");//2

        private Long id;
        private String label;

        private ENUM(Long id, String label) {
            this.id = id;
            this.label = label;
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

    public BorcTipi(ENUM e) {
        this.id = e.getId();
    }

    public BorcTipi() {

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

    public boolean isBorclumu() {
        return this.id.equals(ENUM.BORC.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorcTipi)) return false;

        BorcTipi borcTipi = (BorcTipi) o;

        if (id != null ? !id.equals(borcTipi.id) : borcTipi.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
