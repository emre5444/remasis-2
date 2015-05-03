package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.Sirket;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 09.03.2014.
 */

@Entity
@Table(name = "blok")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Blok.findAll", query = "SELECT r FROM Blok r"),
        @NamedQuery(name = "Blok.findAllWithSirket", query = "SELECT r FROM Blok r where r.sirket.id=:sirketId")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blok implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        A1(1L,"durum.aktif"),//1
        A2(2L,"durum.pasif");//2

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

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;

    @Column(name = "aciklama")
    private String aciklama;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    @ManyToOne
    @JoinColumn(name = "durum" , referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Durum durum;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blok)) return false;

        Blok blok = (Blok) o;

        if (id != null ? !id.equals(blok.id) : blok.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return aciklama;
    }
}
