package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 09.03.2014.
 */

@Entity
@Table(name = "kullanici_tipi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "KullaniciTipi.findAll", query = "SELECT r FROM KullaniciTipi r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KullaniciTipi implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        EVSAHIBI(1L,"durum.aktif"),//1
        KIRACI(2L,"durum.pasif");//2

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

    public KullaniciTipi(ENUM e) {
        this.id = e.getId();
    }

    public KullaniciTipi() {
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

    public boolean isEvsahibiMi(){
       return this.getId().equals(ENUM.EVSAHIBI.getId());
    }

    public static KullaniciTipi getMalikObject() {
        return new KullaniciTipi(ENUM.EVSAHIBI);
    }

    public static KullaniciTipi getSakinObject() {
        return new KullaniciTipi(ENUM.KIRACI);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KullaniciTipi)) return false;

        KullaniciTipi that = (KullaniciTipi) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
