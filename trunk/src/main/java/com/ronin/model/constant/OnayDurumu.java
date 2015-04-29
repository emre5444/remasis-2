package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "onay_durumu")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "OnayDurumu.findAll", query = "SELECT r FROM OnayDurumu r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OnayDurumu implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        ONAYLANMADI(1L, "durum.aktif"),
        ONAY_BEKLIYOR(2L, "durum.aktif"),
        ONAYLANDI(3L, "durum.pasif");

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

    public OnayDurumu(ENUM e) {
        this.id = e.getId();
    }

    public OnayDurumu() {
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

    public static OnayDurumu getOnaylanmadiObject() {
        return new OnayDurumu(ENUM.ONAYLANMADI);
    }

    public static OnayDurumu getOnayBekliyorObject() {
        return new OnayDurumu(ENUM.ONAY_BEKLIYOR);
    }

    public static OnayDurumu getOnaylandiObject() {
        return new OnayDurumu(ENUM.ONAYLANDI);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnayDurumu)) return false;

        OnayDurumu that = (OnayDurumu) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
