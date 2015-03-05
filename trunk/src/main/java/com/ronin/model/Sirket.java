package com.ronin.model;

import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "sirket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sirket implements IAbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "sirket_adi")
    private String sirketAdi;

    @Column(name = "sirket_logo")
    private String sirketLogo;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSirketAdi() {
        return sirketAdi;
    }

    public void setSirketAdi(String sirketAdi) {
        this.sirketAdi = sirketAdi;
    }

    public String getSirketLogo() {
        return sirketLogo;
    }

    public void setSirketLogo(String sirketLogo) {
        this.sirketLogo = sirketLogo;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    @Override
    public String getKisaAciklama() {
        return sirketAdi;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAciklama() {
        return sirketAdi;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sirket)) return false;

        Sirket sirket = (Sirket) o;

        if (!id.equals(sirket.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}